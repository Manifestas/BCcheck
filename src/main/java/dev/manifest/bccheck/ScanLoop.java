package dev.manifest.bccheck;

import dev.manifest.bccheck.data.DbHelper;
import dev.manifest.bccheck.table.Product;
import dev.manifest.bccheck.table.TableModel;
import org.jnativehook.GlobalScreen;
import rx.Observable;
import rx.observables.StringObservable;
import rx.schedulers.Schedulers;
import rx.schedulers.SwingScheduler;

import java.io.IOException;
import java.io.InputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class ScanLoop {

    private static InputStream prepareStream() {
        PipedOutputStream outputStream = new PipedOutputStream();
        GlobalScreen.addNativeKeyListener(new GlobalKeyListener(outputStream));

        PipedInputStream input = null;
        try {
            input = new PipedInputStream(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return input;
    }

    private static void run(InputStream input) {
        Observable<byte[]> byteObservable = StringObservable.from(input);
        Observable<String> stringObservable = StringObservable.decode(byteObservable, StandardCharsets.UTF_8);
        stringObservable.
                debounce(80, TimeUnit.MILLISECONDS)
                .filter(s -> s.length() >= 17)
                .filter(s -> s.matches("([0-9]{12}+)Enter")) // 000004622369Enter
                .map(s -> {
                    int index = s.lastIndexOf("Enter");
                    return s.substring(index - 12, index); // 000004622369
                })
                .map(Product::getPluFromBarcode)    // 462236
                .map(DbHelper::returnProductIfNew)
                .filter(Objects::nonNull)
                .filter(p -> !TableModel.getInstance().containsArticle(p))
                .subscribeOn(Schedulers.io())
                .observeOn(SwingScheduler.getInstance())
                .subscribe(TableModel.getInstance()::addProduct);
    }

    public static void start() {
        run(prepareStream());
    }
}

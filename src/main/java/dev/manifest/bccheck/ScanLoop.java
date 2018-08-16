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
import java.util.logging.Logger;

public class ScanLoop {

    private static final Logger log = Logger.getLogger(ScanLoop.class.getName());

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
                debounce(300, TimeUnit.MILLISECONDS)
                .map(s -> {
                    log.finest("String after \"debounce\": " + s);
                    return s;
                })
                .filter(s -> s.length() >= 12) // remove short single clicks
                .filter(s -> s.matches("(.*[0-9]{7}+)Enter.*")) // *4622369Enter*
                .flatMap(s -> Observable.from(s.split("Enter"))) // split possible
                // 000004622369Enter000004622369Enter to 000004622369, 000004622369
                .map(s -> {
                    log.finest("String after \"split\": " + s);
                    return s;
                })
                .filter(s -> s.length() >= 7 && s.matches("[0-9]{7}.*")) // if we still can get pluId
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

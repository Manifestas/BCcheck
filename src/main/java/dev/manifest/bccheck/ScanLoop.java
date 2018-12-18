package dev.manifest.bccheck;

import dev.manifest.bccheck.data.DbHelper;
import dev.manifest.bccheck.table.Product;
import dev.manifest.bccheck.table.TableModel;
import rx.Observable;
import rx.schedulers.Schedulers;
import rx.schedulers.SwingScheduler;

import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ScanLoop {

    private static final int MAX_INTERVAL_BETWEEN_EVENTS = 200;

    private static final Logger log = Logger.getLogger(ScanLoop.class.getName());

    private static void run() {
        Observable<String> keyObservable = KeyEvents.getKeyObservable();
        keyObservable.buffer(keyObservable.debounce(MAX_INTERVAL_BETWEEN_EVENTS, TimeUnit.MILLISECONDS))
                .map(KeyEvents::getStringFromList)
                .map(s -> {
                    log.finest("String after \"debounce\": " + s);
                    return s;
                })
                .filter(s -> s.length() >= 7 && s.matches("[0-9]{7}.*")) // remove garbage
                .flatMap(s -> Observable.from(s.split("Enter"))) // split possible
                // 000004622369Enter000004622369Enter to 000004622369, 000004622369
                .map(s -> {
                    log.finest("String after \"split\": " + s);
                    return s;
                })
                .map(Product::getPluFromBarcode)    // 462236
                .map(DbHelper::returnProductIfNew)
                .filter(Objects::nonNull)
                .filter(p -> !TableModel.getInstance().containsArticle(p))
                .subscribeOn(Schedulers.from(Executors.newSingleThreadExecutor()))
                .observeOn(SwingScheduler.getInstance())
                .subscribe(n -> TableModel.getInstance().addProduct(n),
                           e -> log.log(Level.WARNING, e.toString()));
    }

    public static void start() {
        run();
    }
}

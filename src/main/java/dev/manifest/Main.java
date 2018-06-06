package dev.manifest;

import dev.manifest.data.DbHelper;
import dev.manifest.table.TableModel;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import rx.Observable;
import rx.observables.StringObservable;
import rx.schedulers.Schedulers;
import rx.schedulers.SwingScheduler;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    public static void main(String[] args) {

        try {
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException e) {
            System.err.println("There was a problem registering nativehook " + e);
            System.exit(1);
        }
        // Get the logger for "org.jnativehook" and set the level to off.
        Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        logger.setLevel(Level.OFF);

        // Don't forget to disable the parent handlers.
        logger.setUseParentHandlers(false);

        PipedOutputStream outputStream = new PipedOutputStream();
        GlobalScreen.addNativeKeyListener(new GlobalKeyListener(outputStream));

        PipedInputStream input = null;
        try {
            input = new PipedInputStream(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }


        EventQueue.invokeLater(() -> {
            MainFrame mainFrame = new MainFrame();
            mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            mainFrame.setVisible(true);
        });


        Observable<byte[]> byteObservable = StringObservable.from(input);
        Observable<String> stringObservable = StringObservable.decode(byteObservable, StandardCharsets.UTF_8);
        stringObservable
                .debounce(80, TimeUnit.MILLISECONDS)
                .filter(s -> s.length() >= 17)
                .filter(s -> s.matches("([0-9]{12}+)Enter")) // 000004622369Enter
                .map(s -> {
                    int index = s.lastIndexOf("Enter");
                    return s.substring(index - 12, index); // 000004622369
                })
                .map(DbHelper::returnProductIfNew)
                .filter(Objects::nonNull)
                .filter(p -> !TableModel.getInstance().containsArticle(p))
                .subscribeOn(Schedulers.io())
                .observeOn(SwingScheduler.getInstance())
                .subscribe(TableModel.getInstance()::addProduct);
    }
}

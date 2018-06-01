package dev.manifest;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import rx.Observable;
import rx.observables.StringObservable;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.nio.charset.StandardCharsets;
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
        Observable<byte[]> byteObservable = StringObservable.from(input);
        Observable<String> stringObservable = StringObservable.decode(byteObservable, StandardCharsets.UTF_8);
        stringObservable
                .debounce(80, TimeUnit.MILLISECONDS)
                .filter(s -> s.length() >= 12)
                .filter(s -> s.matches("([0-9]{12}+)Enter")) // 000004622369Enter
                .map(s -> {
                    int index = s.lastIndexOf("Enter");
                    return s.substring(index - 12, index);
                })
                .subscribe(System.out::println);
    }
}

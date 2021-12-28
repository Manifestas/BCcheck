package dev.manifest.bccheck;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action0;
import rx.subscriptions.Subscriptions;


public class KeyEvents {
    public static Observable<String> getKeyObservable() {
        Observable<String> keyObservable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                final NativeKeyListener keyListener = new NativeKeyListener() {
                    /**
                     * Invoked when a key has been typed.
                     *
                     * @param nativeKeyEvent the native key event.
                     *
                     */
                    @Override
                    public void nativeKeyTyped(NativeKeyEvent nativeKeyEvent) {
                        String keyString = String.valueOf(nativeKeyEvent.getKeyChar());
                        subscriber.onNext(keyString);

                    }

                    /**
                     * Invoked when a key has been pressed.
                     *
                     * @param nativeKeyEvent the native key event.
                     */
                    @Override
                    public void nativeKeyPressed(NativeKeyEvent nativeKeyEvent) {
                    }

                    @Override
                    public void nativeKeyReleased(NativeKeyEvent nativeKeyEvent) {

                    }
                };
                GlobalScreen.addNativeKeyListener(keyListener);
                subscriber.add(Subscriptions.create(new Action0() {
                    @Override
                    public void call() {
                        GlobalScreen.removeNativeKeyListener(keyListener);
                    }
                }));
            }
        });
        return keyObservable;
    }
}

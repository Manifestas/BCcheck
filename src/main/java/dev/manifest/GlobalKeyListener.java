package dev.manifest;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import java.io.IOException;
import java.io.OutputStream;

public class GlobalKeyListener implements NativeKeyListener {

    private OutputStream outputStream;

    public GlobalKeyListener(OutputStream out) {
        outputStream = out;
    }

    @Override
    public void nativeKeyTyped(NativeKeyEvent keyEvent) {

    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent keyEvent) {
        //System.out.println("Key Pressed: " + NativeKeyEvent.getKeyText(keyEvent.getKeyCode()));

        /*
        if (keyEvent.getKeyCode() == NativeKeyEvent.VC_ESCAPE) {
            try {
                GlobalScreen.unregisterNativeHook();
            } catch (NativeHookException e1) {
                e1.printStackTrace();
            }
        }
        */
        String keyString = NativeKeyEvent.getKeyText(keyEvent.getKeyCode());
        try {
            outputStream.write(keyString.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent keyEvent) {

    }
}

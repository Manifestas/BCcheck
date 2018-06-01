package dev.manifest;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

public class GlobalKeyListener implements NativeKeyListener {

    @Override
    public void nativeKeyTyped(NativeKeyEvent keyEvent) {

    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent keyEvent) {
        System.out.println("Key Pressed: " + NativeKeyEvent.getKeyText(keyEvent.getKeyCode()));

        if (keyEvent.getKeyCode() == NativeKeyEvent.VC_ESCAPE) {
            try {
                GlobalScreen.unregisterNativeHook();
            } catch (NativeHookException e1) {
                e1.printStackTrace();
            }
        }
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent keyEvent) {

    }
}

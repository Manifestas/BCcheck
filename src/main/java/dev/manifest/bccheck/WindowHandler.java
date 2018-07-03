package dev.manifest.bccheck;

import javax.swing.*;
import java.io.OutputStream;
import java.util.logging.LogRecord;
import java.util.logging.StreamHandler;

/**
 * Handler for displaying log messages in a JTextArea.
 */
public class WindowHandler extends StreamHandler {

    private JTextArea textArea;

    public WindowHandler(JTextArea area) {
        textArea = area;
        setOutputStream(new OutputStream() {
            @Override
            public void write(int b) {
                // not called!
            }

            @Override
            public void write(byte[] b, int off, int len) {
                textArea.append(new String(b, off, len));
            }
        });
    }

    @Override
    public synchronized void publish(LogRecord record) {
        super.publish(record);
        flush(); // if not use flush messages will only be showing when buffer is full.
    }
}

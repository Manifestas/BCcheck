package dev.manifest;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private static final int DEFAULT_WIDTH = 800;
    private static final int DEFAULT_HEIGHT = 600;

    public MainFrame() {

        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        initTestArea();
    }

    private void initTestArea() {
        JTextArea logTextArea = new JTextArea();
        logTextArea.setLineWrap(true);
        logTextArea.setWrapStyleWord(true);
        logTextArea.setEditable(false);
        logTextArea.setMargin(new Insets(5, 5, 5, 5));

        JScrollPane logScroll = new JScrollPane(logTextArea);
        logScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        logScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

        getContentPane().add(BorderLayout.SOUTH, logScroll);
    }
}

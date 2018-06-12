package dev.manifest.bccheck.ui;

import dev.manifest.bccheck.data.DbHelper;
import dev.manifest.bccheck.table.Table;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainFrame extends JFrame {

    private static final int DEFAULT_WIDTH = 800;
    private static final int DEFAULT_HEIGHT = 600;

    public MainFrame() {

        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        initTextArea();
        initTable();
        initToolbar();
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                DbHelper.dispose();
                try {
                    GlobalScreen.unregisterNativeHook();
                } catch (NativeHookException e1) {
                    e1.printStackTrace();
                }
            }
        });
    }

    private void initTextArea() {
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

    private void initTable() {
        Table table = new Table();
        JScrollPane tableScroll = new JScrollPane(table);
        getContentPane().add(BorderLayout.CENTER, tableScroll);
    }

    private void initToolbar() {
        add(BorderLayout.NORTH, new ToolBar());
    }
}

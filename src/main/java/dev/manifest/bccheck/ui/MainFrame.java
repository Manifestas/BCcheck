package dev.manifest.bccheck.ui;

import dev.manifest.bccheck.WindowHandler;
import dev.manifest.bccheck.data.DbHelper;
import dev.manifest.bccheck.table.Table;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;

import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainFrame extends JFrame {

    private static final int DEFAULT_WIDTH = 800;
    private static final int DEFAULT_HEIGHT = 600;

    private JScrollPane logScroll;
    private JScrollPane tableScroll;
    private ToolBar toolBar;

    public MainFrame() {

        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        initTextArea();
        initTable();
        initToolbar();
        initSplitPane();

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

        // auto scroll down.
        DefaultCaret caret = (DefaultCaret)logTextArea.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

        Handler windowHandler = new WindowHandler(logTextArea);
        windowHandler.setLevel(Level.ALL);
        Logger.getLogger("dev.manifest.bccheck").addHandler(windowHandler);

        logScroll = new JScrollPane(logTextArea);
        logScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        logScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
    }

    private void initTable() {
        Table table = new Table();
        tableScroll = new JScrollPane(table);
    }

    private void initToolbar() {
        toolBar = new ToolBar();
        add(BorderLayout.NORTH, toolBar);
    }

    private void initSplitPane() {
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, true, tableScroll, logScroll);
        splitPane.setOneTouchExpandable(true);
        add(BorderLayout.CENTER, splitPane);
    }
}

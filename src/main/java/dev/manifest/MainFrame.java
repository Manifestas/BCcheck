package dev.manifest;

import dev.manifest.data.DbHelper;
import dev.manifest.table.TableView;

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
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                DbHelper.dispose();
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
        TableView table = new TableView();
        JScrollPane tableScroll = new JScrollPane(table);
        getContentPane().add(BorderLayout.CENTER, tableScroll);
    }
}

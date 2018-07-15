package dev.manifest.bccheck.ui;

import dev.manifest.bccheck.table.TableModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.logging.Logger;

public class ToolBar extends JToolBar {

    private static Logger log = Logger.getLogger(ToolBar.class.getName());

    public ToolBar() {

        // disable toolbar movement
        setFloatable(false);
        initButtons();
    }

    private void initButtons() {
        ImageIcon clearDataIcon = createImageIcon("/image/icon_erase.png");
        JButton clearDataButton = new JButton(clearDataIcon);
        clearDataButton.setFocusable(false);
        clearDataButton.setToolTipText("Clear Data");
        clearDataButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TableModel.getInstance().clearData();
            }
        });
        add(clearDataButton);

        ImageIcon settingsIcon = createImageIcon("/image/icon_settings.png");
        JButton settingsButton = new JButton(settingsIcon);
        settingsButton.setFocusable(false);
        settingsButton.setToolTipText("Open Settings");
        settingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SettingsDialog();
            }
        });
        add(settingsButton);

    }

    /** Returns an ImageIcon, or null if the path was invalid. */
    private ImageIcon createImageIcon(String path) {
        URL imgURL = getClass().getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            log.warning("Couldn't find file: " + path);
            return null;
        }
    }
}

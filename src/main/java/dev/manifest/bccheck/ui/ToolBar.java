package dev.manifest.bccheck.ui;

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
        ImageIcon settingsIcon = createImageIcon("/image/icon_settings.png");
        JButton settingsButton = new JButton(settingsIcon);
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

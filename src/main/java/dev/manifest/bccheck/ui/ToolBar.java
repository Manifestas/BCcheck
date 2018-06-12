package dev.manifest.bccheck.ui;

import javax.swing.*;

public class ToolBar extends JToolBar {

    public ToolBar() {

        // disable toolbar movement
        setFloatable(false);
        initButtons();
    }

    private void initButtons() {
        ImageIcon settingsIcon = createImageIcon("/image/icon_settings.png");
        JButton settingsButton = new JButton(settingsIcon);
        settingsButton.setToolTipText("Open Settings");

        add(settingsButton);
    }

    /** Returns an ImageIcon, or null if the path was invalid. */
    private ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = getClass().getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }
}

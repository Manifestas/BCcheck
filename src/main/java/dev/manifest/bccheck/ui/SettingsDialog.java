package dev.manifest.bccheck.ui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SettingsDialog extends JDialog {

    public SettingsDialog() {
        setTitle("Settings");
        setSize(400, 200);
        initButtons();
        setVisible(true);
    }

    private void initButtons() {
        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        add(saveButton);
        add(cancelButton);

    }
}

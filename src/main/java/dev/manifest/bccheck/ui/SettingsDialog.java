package dev.manifest.bccheck.ui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SettingsDialog extends JDialog {

    public SettingsDialog(JFrame parent) {
        super(parent);
    }

    private void initButtons() {
        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        JButton cancelButon = new JButton("Cancel");
        cancelButon.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        add(saveButton);
        add(cancelButon);

    }
}

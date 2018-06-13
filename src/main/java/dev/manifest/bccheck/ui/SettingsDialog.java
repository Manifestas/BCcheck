package dev.manifest.bccheck.ui;

import dev.manifest.bccheck.data.DbContract;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SettingsDialog extends JDialog {

    public SettingsDialog() {

        JLabel lblIP = new JLabel("Database IP:");
        JTextField tfIP = new JTextField(DbContract.DB_IP);

        JLabel lblPort = new JLabel("Port:");
        JTextField tfPort = new JTextField(DbContract.DB_PORT);

        JLabel lblUser = new JLabel("User:");
        JTextField tfUser = new JTextField(DbContract.DB_LOGIN);

        JLabel lblPassword = new JLabel("Password");
        JPasswordField passwordField = new JPasswordField(DbContract.DB_PASSWORD);

        JLabel lblObject = new JLabel("Object");
        JTextField tfObject = new JTextField(String.valueOf(DbContract.UNIMOLL_ID));

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

        GridBagLayout gridBagLayout = new GridBagLayout();
        setLayout(gridBagLayout);

        add(lblIP, new GBC(0, 0).setInsets(5));
        add(tfIP, new GBC(1, 0, 2, 1)
                .setWeight(10, 0)
                .setFill(GridBagConstraints.HORIZONTAL)
                .setInsets(5));

        add(lblPort, new GBC(0, 1).setInsets(5));
        add(tfPort, new GBC(1, 1, 2, 1)
                .setWeight(10, 0)
                .setFill(GridBagConstraints.HORIZONTAL)
                .setInsets(5));

        add(lblUser, new GBC(0, 2).setInsets(5));
        add(tfUser, new GBC(1, 2, 2, 1)
                .setWeight(10, 0)
                .setFill(GridBagConstraints.HORIZONTAL)
                .setInsets(5));

        add(lblPassword, new GBC(0, 3).setInsets(5));
        add(passwordField, new GBC(1, 3, 2, 1)
                .setWeight(10, 0)
                .setFill(GridBagConstraints.HORIZONTAL)
                .setInsets(5));

        add(lblObject, new GBC(0, 4).setInsets(5));
        add(tfObject, new GBC(1, 4, 2, 1)
                .setWeight(10, 0)
                .setFill(GridBagConstraints.HORIZONTAL)
                .setInsets(5));

        add(saveButton, new GBC(1, 5).setWeight(10, 10).setInsets(20).setAnchor(GridBagConstraints.SOUTHEAST));
        add(cancelButton, new GBC(2, 5).setWeight(0, 10).setInsets(20).setAnchor(GridBagConstraints.SOUTHEAST));

        setTitle("Settings");
        setSize(350, 250);
        setVisible(true);
    }
}

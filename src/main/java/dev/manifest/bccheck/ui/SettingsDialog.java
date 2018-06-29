package dev.manifest.bccheck.ui;

import dev.manifest.bccheck.util.Prefs;

import javax.swing.*;
import java.awt.*;

public class SettingsDialog extends JDialog {

    private JButton saveButton;
    private JButton cancelButton;

    private JLabel lblIP;
    private JTextField tfIP;

    private JLabel lblPort;
    private JTextField tfPort;

    private JLabel lblUser;
    private JTextField tfUser;

    private JLabel lblPassword;
    private JPasswordField passwordField;

    private JLabel lblObject;
    private JTextField tfObject;

    public SettingsDialog() {

        initLabelsTextFields();
        initButtons();
        setViewLocation();

        setTitle("Settings");
        setSize(350, 250);
        setVisible(true);
    }

    private void initLabelsTextFields() {
        lblIP = new JLabel("Database IP:");
        tfIP = new JTextField(Prefs.getIp());

        lblPort = new JLabel("Port:");
        tfPort = new JTextField(Prefs.getPort());

        lblUser = new JLabel("User:");
        tfUser = new JTextField(Prefs.getLogin());

        lblPassword = new JLabel("Password");
        passwordField = new JPasswordField(Prefs.getPassword());

        lblObject = new JLabel("Object");
        tfObject = new JTextField(Prefs.getObject());
    }

    private void initButtons() {
        saveButton = new JButton("Save");
        saveButton.addActionListener(e -> {

        });

        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> SettingsDialog.this.dispose());
    }

    private void setViewLocation() {
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

        add(saveButton, new GBC(1, 5)
                .setWeight(10, 10)
                .setInsets(20)
                .setAnchor(GridBagConstraints.SOUTHEAST));
        add(cancelButton, new GBC(2, 5)
                .setWeight(0, 10)
                .setInsets(20)
                .setAnchor(GridBagConstraints.SOUTHEAST));
    }
}

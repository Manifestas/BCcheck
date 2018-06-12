package dev.manifest.bccheck;

import dev.manifest.bccheck.ui.MainFrame;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class Main {

    private final static Logger log = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {

        try {
            InputStream configFile = Main.class.getResourceAsStream("/logging.properties");
            LogManager.getLogManager().readConfiguration(configFile);
        } catch (IOException e) {
            log.log(Level.WARNING, "Could not open configuration file. Exception: ", e.getMessage());
            log.info("Logging not configured (console output only)");
        }


        try {
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException e) {
            System.err.println("There was a problem registering nativehook " + e);
            System.exit(1);
        }
        // Get the logger for "org.jnativehook" and set the level to off.
        Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        logger.setLevel(Level.OFF);

        // Don't forget to disable the parent handlers.
        logger.setUseParentHandlers(false);


        EventQueue.invokeLater(() -> {
            MainFrame mainFrame = new MainFrame();
            mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            mainFrame.setVisible(true);
        });

        ScanLoop.start();
    }
}

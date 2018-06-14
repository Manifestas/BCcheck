package dev.manifest.bccheck.util;

import dev.manifest.bccheck.data.DbContract;

import java.util.prefs.Preferences;

public class Prefs {

    public static final String IP = "ip";
    public static final String PORT = "port";
    public static final String LOGIN = "login";
    public static final String PASSWORD = "password";

    private static Preferences root = Preferences.userRoot();
    private static Preferences node = root.node("/dev/manifest/bccheck");

    public static String ip() {
        return node.get(IP, DbContract.DB_IP);
    }

    public static String port() {
        return node.get(PORT, DbContract.DB_PORT);
    }

    public static String login() {
        return node.get(LOGIN, DbContract.DB_LOGIN);
    }

    public static String password() {
        return node.get(PASSWORD, DbContract.DB_PASSWORD);
    }

}

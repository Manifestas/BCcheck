package dev.manifest.bccheck.util;

import dev.manifest.bccheck.data.DbContract;

import java.util.prefs.Preferences;

public class Prefs {

    public static final String IP = "getIp";
    public static final String PORT = "getPort";
    public static final String LOGIN = "getLogin";
    public static final String PASSWORD = "getPassword";

    private static Preferences root = Preferences.userRoot();
    private static Preferences node = root.node("/dev/manifest/bccheck");

    public static String getIp() {
        return node.get(IP, DbContract.DB_IP);
    }

    public static String getPort() {
        return node.get(PORT, DbContract.DB_PORT);
    }

    public static String getLogin() {
        return node.get(LOGIN, DbContract.DB_LOGIN);
    }

    public static String getPassword() {
        return node.get(PASSWORD, DbContract.DB_PASSWORD);
    }

    public static void saveIp(String ip) {
        node.put(IP, ip);
    }

    public static void savePort(String port) {
        node.put(PORT, port);
    }

    public static void  saveLogin(String login) {
        node.put(LOGIN, login);
    }

    public static void savePassword(String password) {
        node.put(PASSWORD, password);
    }

}

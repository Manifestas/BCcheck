package dev.manifest.bccheck.util;

import dev.manifest.bccheck.data.DbContract;

import java.util.prefs.Preferences;

public class Prefs {

    private static final String IP = "getIp";
    private static final String PORT = "getPort";
    private static final String LOGIN = "getLogin";
    private static final String PASSWORD = "getPassword";
    private static final String OBJECT = "getObject";

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

    public static String getObject() {
        return node.get(OBJECT, DbContract.UNIMOLL_ID);
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

    public static void saveObject(String object) {
        node.put(OBJECT, object);
    }

    /**
     * Validate an IPv4 address.
     * @param ip the string to validate.
     * @return true if the string validates as an IPV4 address.
     */
    public static boolean isIPV4(final String ip) {
        String pattern = "^((0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)\\.){3}(0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)$";
        return ip.matches(pattern);
    }
}

package dev.manifest.bccheck.util;

public class PrefsValidator {

    /**
     * Validate an IPv4 address.
     * @param ip the string to validate.
     * @return true if the string validates as an IPV4 address.
     */
    public static boolean isIPV4(final String ip) {
        String pattern = "^((0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)\\.){3}(0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)$";
        return ip.matches(pattern);
    }

    /**
     * Checks if port from 1 to 65535.
     * @param port the string to validate.
     * @return true if the portNumber is between 1 and 65535.
     */
    public static boolean isPortValid(final String port) {
        try {
            int portNumber = Integer.parseInt(port);
            if (portNumber >= 1 && portNumber <= 65535) {
                return true;
            }
        } catch (NumberFormatException e) {
            return false;
        }
        return false;
    }

    /**
     * Checks if object number from 0 to 99999.
     * @param object the string to validate.
     * @return true if the object is between 0 and 99999.
     */
    public static boolean isObjectValid(final String object) {
        try {
            int portNumber = Integer.parseInt(object);
            if (portNumber >= 0 && portNumber <= 99999) {
                return true;
            }
        } catch (NumberFormatException e) {
            return false;
        }
        return false;
    }
}

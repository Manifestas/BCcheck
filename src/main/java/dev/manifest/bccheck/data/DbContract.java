package dev.manifest.bccheck.data;


public final class DbContract {

    private DbContract(){}

    /**
     * Database name.
     */
    private static final String DB_NAME = "TradeX";

    //TODO: move this to the SharedPreference and ask a user to enter log/pass in settings menu
    /**
     * User login.
     */
    public static final String DB_LOGIN = "tv";

    /**
     * User password.
     */
    public static final String DB_PASSWORD = "12345678";
    public static final String DB_IP = "192.168.14.2";
    public static final String DB_PORT = "1433";
    public static final String UNIMOLL_ID = "119";

    /**
     * Connection url.
     * %1$s - IP, %2$s - port, %3$s - login, %4$s - password.
     */
    public static final String DB_CONN_URL = "jdbc:sqlserver://%1$s:%2$s;databaseName=" + DB_NAME
                                             + ";user=%3$s;password=%4$s;";

    /** SQL statement. */
    private static final String INNER_JOIN = " INNER JOIN ";
    /** SQL keyword. */
    private static final String ON = " ON ";
    /** A dot for sql command. */
    private static final String DOT = ".";
    /** A comma separator. */
    private static final String COMMA = ", ";
    /** A equal symbol. */
    private static final String EQUALS = " = ";

    /**
     * Query from TradeX table for getting Goods quantity.
     */
    public static final String queryQty =
                "SELECT * FROM "
                + "(SELECT "
                + BarcodeEntry.TABLE_NAME + DOT + BarcodeEntry.COLUMN_BARCODE + COMMA
                + ModelEntry.TABLE_NAME + DOT + ModelEntry.COLUMN_MODEL + COMMA
                + SizeEntry.TABLE_NAME + DOT + SizeEntry.COLUMN_SIZE_NAME + COMMA
                + ColorEntry.TABLE_NAME + DOT + ColorEntry.COLUMN_COLOR + COMMA
                + ObjectEntry.TABLE_NAME + DOT + ObjectEntry.COLUMN_OBJECT + COMMA
                + ObjectEntry.TABLE_NAME + DOT + ObjectEntry.COLUMN_OBJECT_SHORT_DESC + COMMA
                + LogPluCostEntry.TABLE_NAME + DOT + LogPluCostEntry.COLUMN_QUANTITY
                + " FROM " + PluEntry.TABLE_NAME
                + INNER_JOIN + BarcodeEntry.TABLE_NAME
                + ON + BarcodeEntry.TABLE_NAME + DOT + BarcodeEntry.COLUMN_ID_PLU
                + EQUALS + PluEntry.TABLE_NAME + DOT + PluEntry.COLUMN_ID
                + INNER_JOIN + ModelEntry.TABLE_NAME
                + ON + PluEntry.TABLE_NAME + DOT + PluEntry.COLUMN_ID_MODEL
                + EQUALS + ModelEntry.TABLE_NAME + DOT + ModelEntry.COLUMN_ID
                + INNER_JOIN + SizeEntry.TABLE_NAME
                + ON + PluEntry.TABLE_NAME + DOT + PluEntry.COLUMN_ID_SIZE
                + EQUALS + SizeEntry.TABLE_NAME + DOT + SizeEntry.COLUMN_SIZE_ID
                + INNER_JOIN + ColorEntry.TABLE_NAME
                + ON + PluEntry.TABLE_NAME + DOT + PluEntry.COLUMN_COLOR
                + EQUALS + ColorEntry.TABLE_NAME + DOT + ColorEntry.COLUMN_COLOR_ID
                + INNER_JOIN + LogPluCostEntry.TABLE_NAME
                + ON + PluEntry.TABLE_NAME + DOT + PluEntry.COLUMN_ID
                + EQUALS + LogPluCostEntry.TABLE_NAME + DOT + LogPluCostEntry.COLUMN_ID_PLU
                + INNER_JOIN + ObjectEntry.TABLE_NAME
                + ON + LogPluCostEntry.TABLE_NAME + DOT + LogPluCostEntry.COLUMN_OBJECT_ID
                + EQUALS + ObjectEntry.TABLE_NAME + DOT + ObjectEntry.COLUMN_OBJECT_ID
                + " WHERE " + PluEntry.TABLE_NAME + DOT + PluEntry.COLUMN_ID_MODEL + EQUALS
                + " (SELECT " + PluEntry.COLUMN_ID_MODEL
                + " FROM " + PluEntry.TABLE_NAME
                + " WHERE " + PluEntry.COLUMN_ID + EQUALS
                + " (SELECT " + BarcodeEntry.COLUMN_ID_PLU
                + " FROM " + BarcodeEntry.TABLE_NAME
                + " WHERE " + BarcodeEntry.TABLE_NAME + DOT + BarcodeEntry.COLUMN_BARCODE
                + EQUALS  + "? )))"
                + " AS results WHERE " + ObjectEntry.COLUMN_OBJECT + EQUALS + UNIMOLL_ID + ";";


    /**
     * Inner class that defines constant values for the barcode database table.
     */
    public static final class BarcodeEntry {

        /** Name of database table for barcodes*/
        public static final String TABLE_NAME = "T_Barcode";

        /**
         * Barcode number.
         *
         * Type: VARCHAR
         */
        public static final String COLUMN_BARCODE = "Barcode";

        /**
         * Internal code of the article (unique for sizes).
         *
         * Type: INT
         */
        public static final String COLUMN_ID_PLU = "ID_PLU";

        //TODO: add check sum.
        /**
         * For fast searching in database, it is necessary that the length
         * of the barcode string must be 12.
         *
         * @param barcode any length string.
         * @return String barcode with length equal to 12.
         */
        public static String getValidBarcode(String barcode) {
            if (barcode == null || barcode.length() == 0) {
                return null;
            }
            int barcodeLength = barcode.length();
            if (barcodeLength == 12) {
                return barcode;
            } else if (barcodeLength > 12) {
                return barcode.substring(barcodeLength - 12);
            } else {
                return String.format("%0" + (12 - barcodeLength) + "d%s", 0, barcode);
            }
        }

    }

    /**
     * Inner class that defines constant values for the PLU database table.
     */
    public static final class PluEntry {

        /** Name of database table for PLU*/
        public static final String TABLE_NAME = "T_PLU";

        /**
         * Internal code of the article (unique for sizes).
         *
         * Type: INT
         */
        public static final String COLUMN_ID = "ID";

        /**
         * ID of model.
         *
         * Type: INT
         */
        public static final String COLUMN_ID_MODEL = "ID_Models";

        /**
         * ID of color.
         *
         * Type: INT
         */
        public static final String COLUMN_COLOR = "ID_ColorVend";

        /**
         * ID of size.
         *
         * Type: INT
         */
        public static final String COLUMN_ID_SIZE = "ID_Sizes";

        /**
         * TradeX current price.
         *
         * Type: MONEY
         */
        public static final String COLUMN_CURRENT_PRICE = "RecomRetailPrice";
    }

    /**
     * Inner class that defines constant values for the model database table.
     */
    public static final class ModelEntry {

        /** Name of database table for PLU*/
        public static final String TABLE_NAME = "T_Models";

        /**
         * ID of model.
         *
         * Type: INT
         */
        public static final String COLUMN_ID = "ID";

        /**
         * Model name.
         *
         * Type: VARCHAR
         */
        public static final String COLUMN_MODEL = "ModelID";

        /**
         * Model description.
         *
         * Type: VARCHAR
         */
        public static final String COLUMN_MODEL_DESC = "ModelDesc";

        /**
         * ID currency of price(e.g. 3 - euro).
         *
         * Type: INT
         */
        public static final String COLUMN_CURRENCY_ID = "ExpCurrency";

        /**
         * ID of season.
         *
         * Type: INT
         */
        public static final String COLUMN_SEASON_ID = "Season";
    }

    /**
     * Inner class that defines constant values for the colors database table.
     */
    public static final class ColorEntry {

        /** Name of database table for PLU*/
        public static final String TABLE_NAME = "T_ColorVend";

        /**
         * ID of color.
         *
         * Type: INT
         */
        public static final String COLUMN_COLOR_ID = "ID";

        /**
         * Color name(e.g. blue).
         *
         * Type: VARCHAR
         */
        public static final String COLUMN_COLOR = "Color";
    }

    /**
     * Inner class that defines constant values for the Season database table.
     */
    public static final class SeasonEntry {

        /**
         * Name of database table for Seasons
         */
        public static final String TABLE_NAME = "T_Season";

        /**
         * ID of season.
         *
         * Type: INT
         */
        public static final String COLUMN_SEASON_ID = "ID";

        /**
         * Season name(e.g. winter2017-18).
         *
         * Type: VARCHAR
         */
        public static final String COLUMN_SEASON = "RefDesc";
    }

    /**
     * Inner class that defines constant values for the Exchange database table.
     */
    public static final class ExchangeEntry {

        /**
         * Name of database table for Exchange
         */
        public static final String TABLE_NAME = "T_Exchange";

        /**
         * ID of currency.
         *
         * Type: INT
         */
        public static final String COLUMN_CURRENCY_ID = "ID_Currency";

        /**
         * Exchange rate(e.g. 75 in the moment).
         *
         * Type: FLOAT
         */
        public static final String COLUMN_EXCHANGE_RATE = "ExchangeRate";
    }

    /**
     * Inner class that defines constant values for the Sizes database table.
     */
    public static final class SizeEntry {

        /**
         * Name of database table for Sizes.
         */
        public static final String TABLE_NAME = "T_Sizes";

        /**
         * ID of size(e.g. 12).
         *
         * Type: INT
         */
        public static final String COLUMN_SIZE_ID = "ID";

        /**
         * Size name(e.g. 39)
         *
         * Type: VARSHAR
         */
        public static final String COLUMN_SIZE_NAME = "SizeName";
    }

    /**
     * Inner class that defines constant values for the Objects database table.
     */
    public static final class ObjectEntry {

        /**
         * Name of database table for Sizes.
         */
        public static final String TABLE_NAME = "T_Objects";

        /**
         * ID of object(e.g. 24033 (Unimoll)).
         *
         * Type: INT
         */
        public static final String COLUMN_OBJECT_ID = "ID";

        /**
         * Another object ID(e.g. 24033 (Unimoll)).
         */
        public static final String COLUMN_OBJECT = "Object";

        /**
         * Short name of object(e.g. Юнимол)
         *
         * Type: VARSHAR
         */
        public static final String COLUMN_OBJECT_SHORT_DESC = "ShortDesc";

        /**
         * Determines whether to interrogate or not the given store at a conclusion of the rests of the goods.
         * If it equals 2 - then the remainders are queried.
         */
        public static final String COLUMN_KEEP_CHECKS = "KeepChecks";
    }

    /**
     * Inner class that defines constant values for the quantity database table.
     */
    public static final class LogPluCostEntry {

        /**
         * Name of database table for Sizes.
         */
        public static final String TABLE_NAME = "T_LocPLUCost";

        /**
         * Internal code of the article (unique for sizes).
         *
         * Type: INT
         */
        public static final String COLUMN_ID_PLU = "ID_PLU";

        /**
         * ID of object(e.g. 24033 (Unimoll)).
         *
         * Type: INT
         */
        public static final String COLUMN_OBJECT_ID = "ID_Objects";

        /**
         * Quantity of each unique ID of goods
         *
         * Type: FLOAT
         */
        public static final String COLUMN_QUANTITY = "StockQty";
    }
}

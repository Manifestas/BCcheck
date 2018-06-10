package dev.manifest.bccheck.data;

import dev.manifest.bccheck.table.Product;
import dev.manifest.bccheck.data.DbContract.ModelEntry;
import dev.manifest.bccheck.data.DbContract.ColorEntry;
import dev.manifest.bccheck.data.DbContract.SizeEntry;
import dev.manifest.bccheck.data.DbContract.LogPluCostEntry;
import dev.manifest.bccheck.data.DbContract.BarcodeEntry;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DbHelper {

    private final static Logger log = Logger.getLogger(DbHelper.class.getName());

    private static Connection connection;
    private static Statement statement;
    private static ResultSet resultSet;

    public static Connection getConnection() throws Exception {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        connection = DriverManager.getConnection(DbContract.DB_CONN_URL);

        log.finest("Returning connection = " + connection);

        return connection;
    }

    public static void closeConnection() throws SQLException {
        if (connection != null) {
            connection.close();
            log.finest("Closing connection");
        }
    }

    public static void closeStatement() throws SQLException {
        if (statement != null) {
            statement.close();
            log.finest("Closing statement");
        }
    }

    public static void closeResultSet() throws SQLException {
        if (resultSet != null) {
            resultSet.close();
            log.finest("Closing resultSet");
        }
    }

    public static void dispose() {
        try {
            closeResultSet();
            closeStatement();
            closeConnection();
        } catch (SQLException e) {
            log.log(Level.WARNING, "Exception while disposing DB: ", e.getMessage());
        }
    }

    public static ResultSet getResultSet(String barcode) throws SQLException{
        if (connection != null) {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(DbContract.queryQty(barcode));
            return resultSet;
        } else {
            return null;
        }
    }

    public static Product getProductFromResultSet(ResultSet resultSet) throws SQLException {
        String modelName = resultSet.getString(ModelEntry.COLUMN_MODEL);
        String color = resultSet.getString(ColorEntry.COLUMN_COLOR);
        String size = resultSet.getString(SizeEntry.COLUMN_SIZE_NAME);

        return new Product(modelName, color, size);
    }

    public static Product returnProductIfNew(String barcode) {
        Product product = null;
        try {
            if (connection == null) {
                connection = getConnection();
            }
            ResultSet rs = getResultSet(barcode);
            if (rs == null) {
                return null;
            }
            while (rs.next()) {
                if (rs.getInt(LogPluCostEntry.COLUMN_QUANTITY) > 0) {
                    return null;
                }
                if (rs.getString(BarcodeEntry.COLUMN_BARCODE).equals(barcode)) {
                    product = getProductFromResultSet(rs);
                }
            }
        }catch (Exception e) {
            log.log(Level.WARNING, "Exception: ", e.getMessage());
            dispose();
        }
        return product;
    }
}

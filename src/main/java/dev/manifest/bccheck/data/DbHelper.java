package dev.manifest.bccheck.data;

import com.sun.istack.internal.NotNull;
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
    private static PreparedStatement statement;

    private static void dbConnect() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connection = DriverManager.getConnection(DbContract.DB_CONN_URL);

            log.finest("Got connection: " + connection);
        } catch (ClassNotFoundException e) {
            log.log(Level.WARNING, "ClassNotFoundException while loading JDBC driver: ", e.getMessage());
        } catch (SQLException e) {
            log.log(Level.WARNING, "Database access error: ", e.getMessage());
        }
    }

    private static void closeConnection() throws SQLException {
        if (connection != null) {
            connection.close();
            log.finest("Closing connection");
        }
    }

    private static void closeStatement() throws SQLException {
        if (statement != null) {
            statement.close();
            log.finest("Closing statement");
        }
    }

    public static void dispose() {
        try {
            closeStatement();
            closeConnection();
        } catch (SQLException e) {
            log.log(Level.WARNING, "Exception while disposing DB: ", e.getMessage());
        }
    }

    public static void dbReconnect() {
        dispose();
        dbConnect();
    }

    private static ResultSet getResultSet(String barcode) throws SQLException{
        if (statement == null) {
            // using PreparedStatement instead Statement reduces execution time.
            statement = connection.prepareStatement(DbContract.queryQty);
        }
        // replace first question mark placeholder with second argument String.
        statement.setString(1, barcode);
        return statement.executeQuery();
    }

    private static Product getProductFromResultSet(ResultSet resultSet) throws SQLException {
        String modelName = resultSet.getString(ModelEntry.COLUMN_MODEL);
        String color = resultSet.getString(ColorEntry.COLUMN_COLOR);
        String size = resultSet.getString(SizeEntry.COLUMN_SIZE_NAME);

        Product currentProduct = new Product(modelName, color, size);

        log.info("Getting a Product from ResultSet: " + currentProduct);

        return currentProduct;
    }

    /**
     * Returns the product received from the database with this barcode,
     * if there are no any sizes of this article on the residuals
     * @param barcode of product.
     * @return A Product with this barcode, if quantity of any of its sizes is zero.
     */
    public static Product returnProductIfNew(@NotNull String barcode) {
        Product product = null;
        try {
            if (connection == null) {
                log.finest("Connection == null");

                dbConnect();
            }
            try (ResultSet rs = getResultSet(barcode)) {
                if (rs == null) {
                    log.finest("ResultSet == null");

                    return null;
                }
                // check each size
                while (rs.next()) {
                    //if the quantity of goods with this size is greater than zero
                    if (rs.getInt(LogPluCostEntry.COLUMN_QUANTITY) > 0) {
                        log.finest("Quantity with this size > 0");
                        //immediately go out with null
                        return null;
                    }
                    //if barcode matches argument...
                    if (rs.getString(BarcodeEntry.COLUMN_BARCODE).equals(barcode)) {
                        // ... remember it in product
                        product = getProductFromResultSet(rs);
                    }
                }
            }
        }catch (SQLException e) {
            for(Throwable t: e) {
                log.log(Level.WARNING, "SQLException while getting DB query: ", t);
            }
            // close all
            dispose();
        }

        log.finest("Returning a new product: " + product);

        return product;
    }
}

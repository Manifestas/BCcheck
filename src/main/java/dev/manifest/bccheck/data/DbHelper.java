package dev.manifest.bccheck.data;

import dev.manifest.bccheck.table.Product;
import dev.manifest.bccheck.data.DbContract.ModelEntry;
import dev.manifest.bccheck.data.DbContract.ColorEntry;
import dev.manifest.bccheck.data.DbContract.SizeEntry;
import dev.manifest.bccheck.data.DbContract.LogPluCostEntry;
import dev.manifest.bccheck.data.DbContract.PluEntry;
import dev.manifest.bccheck.data.DbContract.ObjectEntry;
import dev.manifest.bccheck.util.Prefs;

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
            String dbUrl = String.format(DbContract.DB_CONN_URL, Prefs.getIp(), Prefs.getPort(), Prefs.getLogin(), Prefs.getPassword());
            connection = DriverManager.getConnection(dbUrl);

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

    private static ResultSet getResultSet(String pluId) throws SQLException {
        if (statement == null) {
            // using PreparedStatement instead Statement reduces execution time.
            statement = connection.prepareStatement(DbContract.queryPluQty);
        }
        // replace first question mark placeholder with second argument String.
        statement.setString(1, pluId);

        return statement.executeQuery();
    }

    private static Product getProductFromResultSet(ResultSet resultSet) throws SQLException {
        String modelName = resultSet.getString(ModelEntry.COLUMN_MODEL);
        String color = resultSet.getString(ColorEntry.COLUMN_COLOR);
        String size = resultSet.getString(SizeEntry.COLUMN_SIZE_NAME);

        Product currentProduct = new Product(modelName, color, size);

        log.finest("Getting a Product from ResultSet: " + currentProduct);

        return currentProduct;
    }

    /**
     * Returns the product received from the database with this pluID,
     * if there are no any sizes of this article on the residuals.
     * We can't use barcode search, because there is no barcode in database for absolute new product
     * while invoice won't be closed. And we can't specify the object immediately in the query,
     * because if the product hasn't been in your object yet the quantity won't be displayed (even zero)
     * and the query returns null.
     *
     * @param pluId of product.
     * @return A Product with this pluID, if quantity of any of its sizes is zero.
     */
    public static Product returnProductIfNew(String pluId) {
        Product product = null;
        try {
            if (connection == null) {
                log.finest("Connection == null");

                dbConnect();
            }
            try (ResultSet rs = getResultSet(pluId)) {
                if (rs == null) {
                    log.finest("ResultSet == null");

                    return null;
                }
                String userObjectID = Prefs.getObject();
                // check each size and each object
                while (rs.next()) {
                    boolean objectIsTheSame = rs.getString(ObjectEntry.COLUMN_OBJECT).equals(userObjectID);
                    //if this is the same object as ours and the quantity of goods with this size is greater than zero
                    if (objectIsTheSame && rs.getInt(LogPluCostEntry.COLUMN_QUANTITY) > 0) {
                        log.info("Quantity with this size > 0");
                        //immediately go out with null
                        return null;
                    }
                    //if we haven't found our product and  ID_PLU matches argument...
                    if (product == null && rs.getString(PluEntry.COLUMN_ID).equals(pluId)) {
                        // ... remember it in product
                        product = getProductFromResultSet(rs);
                    }
                }
            }
        } catch (SQLException e) {
            for (Throwable t : e) {
                log.log(Level.WARNING, "SQLException while getting DB query: ", t);
            }
            // close all
            dispose();
        }
        log.finest("Returning a new product: " + product);
        return product;
    }
}

package dev.manifest.data;

import dev.manifest.table.Product;
import dev.manifest.data.DbContract.ModelEntry;
import dev.manifest.data.DbContract.ColorEntry;
import dev.manifest.data.DbContract.SizeEntry;
import dev.manifest.table.TableModel;

import java.sql.*;

public class DbHelper {

    private static Connection connection;
    private static Statement statement;
    private static ResultSet resultSet;

    public static Connection getConnection() throws Exception {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        connection = DriverManager.getConnection(DbContract.DB_CONN_URL);
        return connection;
    }

    public static void closeConnection() throws SQLException {
        if (connection != null) {
            connection.close();
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
        Product product = null;
        if (resultSet != null && resultSet.next()) {
            String modelName = resultSet.getString(ModelEntry.COLUMN_MODEL);
            String color = resultSet.getString(ColorEntry.COLUMN_COLOR);
            String size = resultSet.getString(SizeEntry.COLUMN_SIZE_NAME);

            product = new Product(modelName, color, size);
        }
        return product;
    }

    public static void addProductToTable(String barcode) {
        Product product = null;
        try {
            getConnection();
            ResultSet rs = getResultSet(barcode);
            product = getProductFromResultSet(rs);
        }catch (Exception e) {
            e.printStackTrace();
        }
        new TableModel().addProduct(product);
    }
}

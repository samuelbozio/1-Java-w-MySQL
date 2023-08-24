package db;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class DB {


    private static Connection connection = null;

    public static Connection getConnection() {
        if (connection == null) {
            try {
                Properties props = loadProperties();
                String url = props.getProperty("url");
                connection = DriverManager.getConnection(url, props);
            } catch (SQLException sqlException) {
                throw new DbException(sqlException.getMessage());
            }
        }

        return connection;
    }

    private static Properties loadProperties() {
        try (InputStream input = DB.class.getClassLoader().getResourceAsStream("db.properties")) {
            Properties props = new Properties();
            props.load(input);
            return props;
        } catch (IOException e) {
            throw new DbException(e.getMessage());
        }

    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException sqlException) {
                throw new DbException(sqlException.getMessage());
            }
        }

    }

    public static void closeStatement(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            }
        }
    }

    public static void closeResultSet(ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            }
        }
    }
}

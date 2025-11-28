package com.ac.cst8319.lms.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Database connection utility class.
 * Manages JDBC connections using configuration from db.properties.
 */
public class DatabaseConnection {

    private static final String PROPERTIES_FILE = "db.properties";
    private static Properties properties = new Properties();

    static {
        // Load database properties when class is initialized
        try (InputStream input = DatabaseConnection.class.getClassLoader()
                .getResourceAsStream(PROPERTIES_FILE)) {
            if (input == null) {
                throw new RuntimeException("Unable to find " + PROPERTIES_FILE);
            }
            properties.load(input);

            // Load MySQL JDBC driver
            Class.forName(properties.getProperty("db.driver"));

        } catch (IOException e) {
            throw new RuntimeException("Error loading database properties", e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("MySQL JDBC Driver not found", e);
        }
    }

    /**
     * Get a database connection from the connection pool.
     * @return Connection object
     * @throws SQLException if connection cannot be established
     */
    public static Connection getConnection() throws SQLException {
        String url = properties.getProperty("db.url");
        String username = properties.getProperty("db.username");
        String password = properties.getProperty("db.password");

        return DriverManager.getConnection(url, username, password);
    }

    /**
     * Close database resources safely.
     * @param conn Connection to close
     */
    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                System.err.println("Error closing connection: " + e.getMessage());
            }
        }
    }

    /**
     * Close PreparedStatement safely.
     * @param stmt PreparedStatement to close
     */
    public static void closePreparedStatement(PreparedStatement stmt) {
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                System.err.println("Error closing prepared statement: " + e.getMessage());
            }
        }
    }

    /**
     * Close ResultSet safely.
     * @param rs ResultSet to close
     */
    public static void closeResultSet(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                System.err.println("Error closing result set: " + e.getMessage());
            }
        }
    }

    /**
     * Close all resources safely (Connection, PreparedStatement, ResultSet).
     * @param conn Connection to close
     * @param stmt PreparedStatement to close
     * @param rs ResultSet to close
     */
    public static void closeResources(Connection conn, PreparedStatement stmt, ResultSet rs) {
        closeResultSet(rs);
        closePreparedStatement(stmt);
        closeConnection(conn);
    }

    /**
     * Close resources safely (Connection and PreparedStatement).
     * @param conn Connection to close
     * @param stmt PreparedStatement to close
     */
    public static void closeResources(Connection conn, PreparedStatement stmt) {
        closePreparedStatement(stmt);
        closeConnection(conn);
    }

    /**
     * Test database connectivity.
     * @return true if connection is successful, false otherwise
     */
    public static boolean testConnection() {
        try (Connection conn = getConnection()) {
            return conn != null && !conn.isClosed();
        } catch (SQLException e) {
            System.err.println("Database connection test failed: " + e.getMessage());
            return false;
        }
    }
}

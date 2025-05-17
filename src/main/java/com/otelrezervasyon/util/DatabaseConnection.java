package com.otelrezervasyon.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/otel_rezervasyon";
    private static final String USER = "root";
    private static final String PASSWORD = "root";
    
    private static Connection connection = null;
    
    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Veritabanı bağlantısı başarıyla kuruldu/yenilendi!");
            }
        } catch (SQLException e) {
            System.err.println("Veritabanı bağlantı hatası: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
        return connection;
    }
    
    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Veritabanı bağlantısı kapatıldı.");
            } catch (SQLException e) {
                System.err.println("Bağlantı kapatma hatası: " + e.getMessage());
            }
        }
    }
} 
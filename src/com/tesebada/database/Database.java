package com.tesebada.database;

import java.sql.*;

public class Database {

    private final String connectionUrl = "jdbc:sqlserver://localhost:1433;databaseName=tesebada;user=sa;password=root";
    private static Database instance;
    private Connection connection;

    private Database() {
        try {
            connection = DriverManager.getConnection(connectionUrl);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }

}

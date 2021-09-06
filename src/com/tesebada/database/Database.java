package com.tesebada.database;

import java.sql.*;

public class Database {

    private static Database instance;
    private Connection connection;
    private String SERVER = "25.7.138.176";
    private String PORT = "1433";
    private String DATABASE_NAME = "tesebada";
    private String USER = "SA";
    private String PASSWORD = "PasswordO1.";

    private Database() {
        try {
            connection = DriverManager.getConnection(this.getConnectionUrl());
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

    private String getConnectionUrl() {
        return String.format(
            "jdbc:sqlserver://%s:%s;databaseName=%s;user=%s;password=%s",
            this.SERVER,
            this.PORT,
            this.DATABASE_NAME,
            this.USER,
            this.PASSWORD
        );
    }

    public Connection getConnection() {
        return connection;
    }

}

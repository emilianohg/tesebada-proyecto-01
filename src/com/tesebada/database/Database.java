package com.tesebada.database;

import com.tesebada.environment.Environment;

import java.sql.*;

public class Database {

    private static Database instance;
    Environment env;

    private Connection connection;
    private final String SERVER;
    private final String PORT;
    private final String DATABASE_NAME;
    private final String USER;
    private final String PASSWORD;
    public final String RESPONSABLE;

    private Database() {
        this.env = new Environment();

        this.SERVER = env.getValue("SERVER");
        this.PORT = env.getValue("PORT");
        this.USER = env.getValue("USER");
        this.PASSWORD = env.getValue("PASSWORD");
        this.DATABASE_NAME = env.getValue("DATABASE_NAME");
        this.RESPONSABLE = env.getValue("RESPONSABLE");

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

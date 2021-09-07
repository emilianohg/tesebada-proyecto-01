package com.tesebada.database;


import javax.swing.table.DefaultTableModel;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class StatusAccount {

    Database db = Database.getInstance();


    public StatusAccount(DefaultTableModel TableModel){
        String sql = "SELECT retiros.*, usuarios.nombre as responsable FROM retiros INNER JOIN usuarios ON retiros.id_usuario = usuarios.id";
        try {
            Statement stmt = db.getConnection().createStatement();

            ResultSet res =  stmt.executeQuery(sql);

            while (res.next()){

                String nocuenta    = String.valueOf(res.getString("nocuenta"));
                String cantidad    = String.valueOf(res.getDouble("cantidad"));
                String responsable = String.valueOf(res.getString("responsable"));
                String created_at  = String.valueOf(res.getTimestamp("created_at"));

                String retiro[] = {nocuenta,cantidad,responsable,created_at};

                TableModel.addRow(retiro);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


    }


}

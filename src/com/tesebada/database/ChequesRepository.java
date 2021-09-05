package com.tesebada.database;

import java.io.Reader;
import java.sql.*;
import java.util.Optional;

public class ChequesRepository {

    Database db = Database.getInstance();

    public Optional<Cheque> getCheque(String noCuenta) {

        String sql = String.format("SELECT * FROM cheques WHERE noCuenta = %s", noCuenta);

        try {
            Statement stmt = db.getConnection().createStatement();
            ResultSet result = stmt.executeQuery(sql);

            if (result.next()) {
                String _noCuenta = result.getString("noCuenta");
                double _importe = result.getDouble("importe");
                String _estatus = result.getString("estatus");

                return Optional.of(new Cheque(_noCuenta, _importe, _estatus.charAt(0)));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return Optional.empty();
    }

    public Optional<Cheque> getChequeV2(String noCuenta,int quantity)  {
     String query = "{call withdraw(?,?,?,?) }";

        CallableStatement statements ;

        try {

            statements = db.getConnection().prepareCall(query);

            statements.setString(1, noCuenta);
            statements.setInt(2,quantity);

            statements.registerOutParameter(3, Types.DOUBLE);
            statements.registerOutParameter(4,Types.CHAR);

            boolean res = statements.execute();

            if(!res) {

                double _importe = statements.getDouble(3);
                String _estatus = statements.getString(4);

                if (_estatus == null) {
                    return Optional.empty();
                }

                return Optional.of(new Cheque(noCuenta,_importe,_estatus.charAt(0)));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return Optional.empty();
    }

    public Optional<Cheque> withdraw(String noCuenta, int quantity) {

        String sql = "DECLARE @importe_total MONEY;" +
                "DECLARE @estatus CHAR(1);" +
                "EXEC withdraw \""+ noCuenta +"\", "+ quantity +", @importe_total OUTPUT, @estatus OUTPUT;" +
                "SELECT @importe_total AS importe, @estatus AS estatus;";

        System.out.println(sql);

        try {
            Statement stmt = db.getConnection().createStatement();

            ResultSet result = stmt.executeQuery(sql);

            if (result.next()) {
                double _importe = result.getDouble("importe");
                String _estatus = result.getString("estatus");

                return Optional.of(new Cheque(noCuenta, _importe, _estatus.charAt(0)));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return Optional.empty();
    }


}

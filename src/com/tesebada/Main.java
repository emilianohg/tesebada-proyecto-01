package com.tesebada;

import com.tesebada.database.Cheque;
import com.tesebada.database.ChequesRepository;
import com.tesebada.views.WindowAccount;

import java.sql.SQLException;
import java.util.Optional;

public class Main {
    public static void main(String[] args) throws SQLException {

        ChequesRepository chequesRepository = new ChequesRepository();

        new WindowAccount();

    }
}
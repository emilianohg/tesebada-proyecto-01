package com.tesebada;

import com.tesebada.database.Cheque;
import com.tesebada.database.ChequesRepository;
import com.tesebada.views.WindowAccount;

import java.sql.SQLException;
import java.util.Optional;

public class Main {
    public static void main(String[] args) throws SQLException {

        ChequesRepository chequesRepository = new ChequesRepository();

        // new WindowAccount();

        for (int i = 0; i < 10; i++) {
            chequesRepository.getChequeV2("00001", 1);
            Optional<Cheque> cheque = chequesRepository.getCheque("00001");
            cheque.ifPresent(System.out::println);
        }

    }
}
package com.tesebada;

import com.tesebada.database.Cheque;
import com.tesebada.database.ChequesRepository;
import com.tesebada.views.WindowAccount;

import java.util.Optional;

public class Main {
    public static void main(String[] args) {

        ChequesRepository chequesRepository = new ChequesRepository();

        // new WindowAccount();

        for (int i = 0; i < 10; i++) {
            Optional<Cheque> cheque = chequesRepository.withdraw("00001", 1);
            cheque.ifPresent(System.out::println);
        }

    }
}
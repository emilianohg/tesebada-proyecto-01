package com.tesebada.views;

import com.tesebada.database.Cheque;
import com.tesebada.database.ChequesRepository;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.Optional;

public class WindowAccount extends JFrame {

    private JPanel PanelCenter,PanelBottom;
    boolean flag =  false;
    JTable table;
    ChequesRepository chequesRepository = new ChequesRepository();

    DefaultTableModel tableModel;


    private JTextField noCuenta,retiro;

    JLabel error;
    private JButton btn;

    private

    String[] columnas = {"No. Cuenta", "Importe", "Estatus"};
    Object [] data = {};

    public WindowAccount() {
        init();
        setListeners();
    }

    public void setListeners(){
        this.btn.addActionListener(event -> startRestCicle());
    }

    public void startRestCicle(){
        String _chequera = noCuenta.getText();
        String _retiro = retiro.getText();
        error.setText("");
        if(!flag) {



            if (_chequera.isEmpty() || _retiro.isEmpty()) {

                error.setText("Datos no validos");
                System.out.println("manejar error");
                return;
            }

            Optional<Cheque> cheque = chequesRepository.getChequeV2(_chequera, Integer.parseInt(_retiro));
            cheque.ifPresent(valor -> {

                data = new Object[]{valor.getNoCuenta(), valor.getImporte(), valor
                        .getEstatus()};
                tableModel.addRow(data);

            });


            flag = true;
            retiro.setEnabled(true);
        } else {

            for (int i = 0; i < 10 ; i++) {
                Optional<Cheque> cheque = chequesRepository.getChequeV2(_chequera, Integer.parseInt(_retiro));
                cheque.ifPresent(valor -> {

                    data = new Object[]{valor.getNoCuenta(), valor.getImporte(), valor
                            .getEstatus()};
                    tableModel.addRow(data);
                    table.update(this.getGraphics());

                });
            }

            System.out.println("Inicia ciclo");
        }

    }

    public void init() {
        this.setSize(500, 500);
        this.setLayout(new BorderLayout());
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        PanelCenter = new JPanel();
        PanelBottom = new JPanel();

        PanelCenter.setLayout(new BorderLayout());
        PanelBottom.setLayout(new GridLayout(0,3));

         tableModel = new DefaultTableModel(columnas, 0);

        tableModel.addRow(data);

       table = new JTable(tableModel);
        JTableHeader tableHeader = table.getTableHeader();

        PanelCenter.add(tableHeader,BorderLayout.NORTH);
        PanelCenter.add(table,BorderLayout.CENTER);

        btn = new JButton("Get Chequera");
        noCuenta = new JTextField();
        retiro=new JTextField();


        retiro.setText("0");
        retiro.setEnabled(false);
        error = new JLabel();

        error.setForeground(Color.red);


        PanelBottom.add(new JLabel("No.Cuenta"));
        PanelBottom.add(noCuenta);
        PanelBottom.add(error);
        PanelBottom.add(new JLabel("Retiro $: "));
        PanelBottom.add(retiro);
        PanelBottom.add(btn);



        this.add(PanelCenter,BorderLayout.CENTER);
        this.add(PanelBottom,BorderLayout.SOUTH);

        this.setVisible(true);

    }

}

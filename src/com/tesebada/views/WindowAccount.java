package com.tesebada.views;

import com.tesebada.database.Cheque;
import com.tesebada.database.ChequesRepository;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.Optional;

public class WindowAccount extends JFrame {

    private JPanel PanelCenter, PanelBottom;
    boolean flag =  false;
    private JScrollPane scrollTable;
    private JTable table;
    private ChequesRepository chequesRepository = new ChequesRepository();

    private DefaultTableModel tableModel;

    private JTextField noCuenta,retiro;

    private JLabel error;
    private JButton btn;

    private Object [] data = {};

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
                return;
            }

            Optional<Cheque> cheque = chequesRepository.getChequeV2(_chequera, Integer.parseInt(_retiro));
            cheque.ifPresent(valor -> {
                data = new Object[]{ valor.getNoCuenta(), valor.getImporte(), valor.getEstatus() };
                tableModel.addRow(data);
                table.update(table.getGraphics());

            });

            if (!cheque.isPresent()) {
                error.setText("No existe la cuenta ingresada");
                return;
            }


            flag = true;
            retiro.setEnabled(true);
        } else {

            for (int i = 0; i < 10 ; i++) {
                Optional<Cheque> cheque = chequesRepository.getChequeV2(_chequera, Integer.parseInt(_retiro));
                cheque.ifPresent(valor -> {

                    data = new Object[]{valor.getNoCuenta(), valor.getImporte(), valor.getEstatus()};
                    tableModel.addRow(data);
                    this.revalidate();
                    this.update(this.getGraphics());
                    this.repaint();

                });

                if (!cheque.isPresent()) {
                    error.setText("No existe la cuenta ingresada");
                    return;
                }
            }

        }

    }

    public void init() {
        this.setSize(700, 500);
        this.setLayout(new BorderLayout());
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        PanelCenter = new JPanel();
        PanelBottom = new JPanel();

        PanelCenter.setLayout(new BorderLayout());
        PanelBottom.setLayout(new GridLayout(0,3));

        tableModel = new DefaultTableModel(new String[]{"No. Cuenta", "Importe", "Estatus"}, 0);

        tableModel.addRow(data);

        table = new JTable(tableModel);
        JTableHeader tableHeader = table.getTableHeader();

        scrollTable = new JScrollPane(table);

        PanelCenter.add(tableHeader, BorderLayout.NORTH);
        PanelCenter.add(scrollTable, BorderLayout.CENTER);

        btn = new JButton("Retirar");
        noCuenta = new JTextField();
        retiro = new JTextField();


        retiro.setText("0");
        retiro.setEnabled(false);
        error = new JLabel();

        error.setForeground(Color.red);


        PanelBottom.add(new JLabel("No. Cuenta"));
        PanelBottom.add(noCuenta);
        PanelBottom.add(error);
        PanelBottom.add(new JLabel("Cantidad $: "));
        PanelBottom.add(retiro);
        PanelBottom.add(btn);

        this.add(PanelCenter, BorderLayout.CENTER);
        this.add(PanelBottom, BorderLayout.NORTH);

        this.setVisible(true);

    }

}

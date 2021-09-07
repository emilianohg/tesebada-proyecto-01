package com.tesebada.views;

import com.tesebada.database.StatusAccount;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;
import java.awt.*;

public class Reporte extends JFrame {
    private JPanel PanelCenter;
    private JTableHeader TableHeader;
    private JScrollPane ScrollTable;
    private JTable Table;

    private DefaultTableModel TableModel;



    Reporte(){
        init();
    }

    private void init(){
        this.setSize(560, 500);
        this.setLayout(new BorderLayout());
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        PanelCenter = new JPanel();

        PanelCenter.setLayout(new BorderLayout());

        TableModel  = new DefaultTableModel(new String[]{"nocuenta", "cantidad", "responsable","created_at"}, 0);
        Table       = new JTable(TableModel);
        ScrollTable = new JScrollPane(Table);
        TableHeader = Table.getTableHeader();

        PanelCenter.add(TableHeader,BorderLayout.NORTH);
        PanelCenter.add(ScrollTable,BorderLayout.CENTER);

        new StatusAccount(TableModel);

        this.add(PanelCenter);


        setVisible(true);
    }
}

package com.tesebada.views;

import javax.swing.*;
import java.awt.*;

public class WindowAccount extends JFrame {

    public WindowAccount() {


    }

    public void init() {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());

        JLabel label = new JLabel("No. Cuenta");

        JButton button = new JButton();

        button.setText("Button");
        panel.add(label);
        panel.add(button);

        this.add(panel);
        this.setSize(200, 300);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

}

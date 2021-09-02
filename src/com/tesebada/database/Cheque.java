package com.tesebada.database;

public class Cheque {
    private String noCuenta;
    private double importe;
    private char estatus;

    public Cheque(String noCuenta, double importe, char estatus) {
        this.noCuenta = noCuenta;
        this.importe = importe;
        this.estatus = estatus;
    }

    public String getNoCuenta() {
        return noCuenta;
    }

    public double getImporte() {
        return importe;
    }

    public char getEstatus() {
        return estatus;
    }

    @Override
    public String toString() {
        return "Cheque{" +
                "noCuenta='" + noCuenta + '\'' +
                ", importe=" + importe +
                ", estatus=" + estatus +
                '}';
    }
}

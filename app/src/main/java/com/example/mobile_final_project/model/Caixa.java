package com.example.mobile_final_project.model;

public class Caixa {

    int id;
    double saldo,despesa;


    public Caixa() {}

    public Caixa(double saldo, double despesa) {
        this.saldo = saldo;
        this.despesa = despesa;
    }

    public int getId() {
        return id;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public double getDespesa() {
        return despesa;
    }

    public void setDespesa(double despesa) {
        this.despesa = despesa;
    }
}

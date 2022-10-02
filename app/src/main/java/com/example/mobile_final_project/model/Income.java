package com.example.mobile_final_project.model;

import java.util.Date;

public class Income extends Transaction{

    public static Double amount;
    public Income(String descricao, Date data, double valor) {
        super(descricao, data, valor);
        amount = valor;
    }

    public Income(String descricao, Date data, double valor, boolean isReceived) {
        super(descricao, data, valor, isReceived);
    }


}

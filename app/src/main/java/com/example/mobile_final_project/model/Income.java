package com.example.mobile_final_project.model;

import java.util.Date;

public class Income extends Transaction{

    public static Double amount;
    public Income(int id, String description, Date date, double value) {
        super(id, description, date, value);
        amount = value;
    }

    public Income(int id, String descricao, Date data, double valor, boolean isReceived) {
        super(id, descricao, data, valor, isReceived);
    }


}

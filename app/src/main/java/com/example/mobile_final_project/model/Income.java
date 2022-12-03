package com.example.mobile_final_project.model;

import java.util.Date;

public class Income extends Transaction{

    public static Double amount;
    public Income(int id, String description, String category, Date date, double value) {
        super(id, description, category, date, value);
        amount = value;
    }

    public Income(int id, String description, String category, Date data, double valor, boolean isReceived) {
        super(id, description, category, data, valor, isReceived);
    }


}

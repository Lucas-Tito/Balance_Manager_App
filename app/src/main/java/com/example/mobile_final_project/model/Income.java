package com.example.mobile_final_project.model;

import java.util.Date;

public class Income extends Transaction{

    public static Double amount;
    public Income(int id, String description, String category, String location, Date date, double value) {
        super(id, description, category, location, date, value);
        amount = value;
    }

    public Income(int id, String description, String category, String location, Date data, double valor, boolean isReceived) {
        super(id, description, category, location, data, valor, isReceived);
    }


}

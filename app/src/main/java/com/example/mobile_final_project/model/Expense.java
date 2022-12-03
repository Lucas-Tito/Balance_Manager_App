package com.example.mobile_final_project.model;

import java.io.Serializable;
import java.util.Date;

public class Expense extends Transaction {

    public Expense(int id, String description, String category, Date date, double value) {
        super(id, description, category, date, value);
    }

    public Expense(int id, String description, String category, Date date, double value, boolean isPaid) {
        super(id, description, category, date, value, isPaid);
    }

}

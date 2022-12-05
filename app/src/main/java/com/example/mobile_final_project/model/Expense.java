package com.example.mobile_final_project.model;

import java.io.Serializable;
import java.util.Date;

public class Expense extends Transaction {

    public Expense(int id, String description, String category, String location, Date date, double value) {
        super(id, description, category, location, date, value);
    }

    public Expense(int id, String description, String category, String location, Date date, double value, boolean isPaid) {
        super(id, description, category, location, date, value, isPaid);
    }

}

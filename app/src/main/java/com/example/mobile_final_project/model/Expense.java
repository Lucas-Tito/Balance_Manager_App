package com.example.mobile_final_project.model;

import java.io.Serializable;
import java.util.Date;

//implements Serializable for it to be passed between activities and fragments
public class Expense extends Transaction implements Serializable {



    public Expense(int id, String description, Date date, double value) {
        super(id, description, date, value);
    }

    public Expense(int id, String description, Date date, double value, boolean isPaid) {
        super(id, description, date, value, isPaid);
    }

}

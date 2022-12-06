package com.example.mobile_final_project.viewmodel;

import java.util.Date;

public class TransactionDBViewModel {
    public int id;
    public String description, category, location,type;
    public Date entryDate;
    public double value;
    public boolean isPaid;

    @Override
    public String toString() {
        return "TransactionDBViewModel{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", category='" + category + '\'' +
                ", location='" + location + '\'' +
                ", type='" + type + '\'' +
                ", entryDate=" + entryDate +
                ", value=" + value +
                ", isPaid=" + isPaid +
                '}';
    }
}

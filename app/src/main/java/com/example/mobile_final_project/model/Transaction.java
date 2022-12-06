package com.example.mobile_final_project.model;

import java.io.Serializable;
import java.util.Date;

public class Transaction implements Serializable {

    private int id;
    private String description, category, location;
    private Date entryDate;
    private double value;
    private boolean isPaid;

    public Transaction(){}

    Transaction(int id, String description, String category, String location, Date date, double value){

        this.description = description;
        this.category = category;
        this.location = location;
        this.entryDate = date;
        this.value = value;
        this.isPaid = true;

    }

    Transaction(int id, String description, String category, String location, Date date, double value, boolean isPaid){

        this.description = description;
        this.category = category;
        this.location = location;
        this.entryDate = date;
        this.value = value;
        this.isPaid = isPaid;

    }




    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getId() {
        return id;
    }

    /*
    public void setId(int id) {
        this.id = id;
    }*/

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public Date getDate() {
        return entryDate;
    }

    public void setDate(Date date) {
        this.entryDate = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean getIsPaid(){
        return isPaid;
    }

    public void setIsPaid(boolean value){
        this.isPaid = value;
    }


    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", category='" + category + '\'' +
                ", location='" + location + '\'' +
                ", entryDate=" + entryDate +
                ", value=" + value +
                ", isPaid=" + isPaid +
                '}';
    }
}



package com.example.mobile_final_project.model;

import java.io.Serializable;
import java.util.Date;

public class Transaction implements Serializable {

    private int id;
    private String description, category;
    private Date entryDate;
    private double value;
    private boolean isPaid;

    Transaction(int id, String description, Date date, double value){

        this.description = description;
        this.entryDate = date;
        this.value = value;
        this.isPaid = true;

    }

    Transaction(int id, String description, Date date, double value, boolean isPaid){

        this.description = description;
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
        return "Receita{" +
                "valor=" + value +
                ", dataEntrada=" + entryDate +
                ", descricao='" + description + '\'' +
                '}';
    }
}



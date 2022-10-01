package com.example.mobile_final_project.model;

import java.io.Serializable;
import java.util.Date;

//implements Serializable for it to be passed between activities and fragments
public class Expense extends Transaction implements Serializable {



    public Expense(String descricao, Date data, double valor) {
        super(descricao, data, valor);
    }

    public Expense(String descricao, Date data, double valor, boolean isPaid) {
        super(descricao, data, valor, isPaid);
    }

}

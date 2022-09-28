package com.example.mobile_final_project.model;

import java.io.Serializable;
import java.util.Date;

//implements Serializable for it to be passed between activities and fragments
public class Despesa extends Transaction implements Serializable {

    public Despesa(){

    }

    public Despesa(String descricao, Date data, double valor) {
        super(descricao, data, valor);
    }

}

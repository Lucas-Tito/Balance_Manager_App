package com.example.mobile_final_project.model;

import java.io.Serializable;
import java.util.Date;

abstract class Transaction implements Serializable {

    /*commented while data base isn't developed*/
    /*private int id;*/
    private String descricao, categoria;
    private Date dataEntrada;
    private double valor;
    private boolean isPaid;

    Transaction(String descricao, Date data, double valor){

        this.descricao = descricao;
        this.dataEntrada = data;
        this.valor = valor;
        this.isPaid = true;

    }




    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

/*    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }*/

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public Date getData() {
        return dataEntrada;
    }

    public void setData(Date data) {
        this.dataEntrada = data;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
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
                "valor=" + valor +
                ", dataEntrada=" + dataEntrada +
                ", descricao='" + descricao + '\'' +
                '}';
    }
}



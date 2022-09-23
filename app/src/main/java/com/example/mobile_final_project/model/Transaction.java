package com.example.mobile_final_project.model;

import java.util.Date;

abstract class Transaction {

    /*commented while data base isn't developed*/
    /*private int id;*/
    private String descricao, categoria;
    private Date dataEntrada;
    private double valor;

    Transaction(String descricao, Date data, double valor){

        this.descricao = descricao;
        this.dataEntrada = data;
        this.valor = valor;

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


    @Override
    public String toString() {
        return "Receita{" +
                "valor=" + valor +
                ", dataEntrada=" + dataEntrada +
                ", descricao='" + descricao + '\'' +
                '}';
    }
}



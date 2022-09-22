package com.example.mobile_final_project.model;

import java.util.Date;

public class Receita {
    int id;
    double valor;
    Date dataEntrada;
    String descricao, categoria;

    public Receita(double valor, Date dataEntrada, String descricao) {
        this.valor = valor;
        this.dataEntrada = dataEntrada;
        this.descricao = descricao;
    }

    public int getId()
    {
        return id;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public Date getDataEntrada() {
        return dataEntrada;
    }

    public void setDataEntrada(Date dataEntrada) {
        this.dataEntrada = dataEntrada;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
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

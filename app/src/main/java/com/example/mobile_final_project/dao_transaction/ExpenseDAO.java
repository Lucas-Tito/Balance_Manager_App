package com.example.mobile_final_project.dao_transaction;

import com.example.mobile_final_project.model.Despesa;

import java.util.ArrayList;
import java.util.Date;

public class ExpenseDAO {

    ArrayList<Despesa> expenses = new ArrayList<>();
    Date date = new Date(10, 9, 2003);

    Despesa teste = new Despesa("teste", date, 20.03);

    public ExpenseDAO(){
        expenses.add(teste);
    }

    public void addExpense(Despesa despesa){

        expenses.add(despesa);

    }

    public ArrayList<Despesa> getAll(){
        return expenses;
    }

}

package com.example.mobile_final_project.dao_transaction;

import com.example.mobile_final_project.model.Despesa;

import java.util.ArrayList;
import java.util.Date;

public class ExpenseDAO {

    ArrayList<Despesa> expenses = new ArrayList<>();
    Date date = new Date(10, 9, 2003);

    Despesa teste = new Despesa("teste", date, 20.03);
    Despesa teste2 = new Despesa("teste2", date, 5.00);

    public ExpenseDAO(){
        expenses.add(teste);
        expenses.add(teste2);
        expenses.add(teste2);
        expenses.add(teste2);
        expenses.add(teste2);
        expenses.add(teste2);
        expenses.add(teste2);
    }

    public void addExpense(Despesa despesa){

        expenses.add(despesa);

    }

    public ArrayList<Despesa> getAll(){
        return expenses;
    }

}

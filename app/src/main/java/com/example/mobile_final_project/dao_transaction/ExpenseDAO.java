package com.example.mobile_final_project.dao_transaction;

import com.example.mobile_final_project.model.Despesa;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

//class implements Serializable so it can be passed from Activities to Fragments
public class ExpenseDAO implements Serializable {

    ArrayList<Despesa> expenses = new ArrayList<>();

    public ExpenseDAO(Despesa despesa){

            expenses.add(despesa);
    }

    public void addExpense(Despesa despesa){

        expenses.add(despesa);

    }

    public void removeExpense(int id){

        expenses.remove(id);

    }

    public void editExpense(int id, int amount, boolean isPaid, String description, String category){

        expenses.get(id).setValor(amount);
        expenses.get(id).setIsPaid(isPaid);
        expenses.get(id).setDescricao(description);
        expenses.get(id).setCategoria(category);

    }


    public Despesa get(int pos){
        return expenses.get(pos);
    }

    public ArrayList<Despesa> getAll(){
        return expenses;
    }

}

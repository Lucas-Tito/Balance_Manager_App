package com.example.mobile_final_project.dao_transaction;

import com.example.mobile_final_project.model.Expense;

import java.io.Serializable;
import java.util.ArrayList;

//class implements Serializable so it can be passed from Activities to Fragments
public class ExpenseDAO implements Serializable {

    ArrayList<Expense> expenses = new ArrayList<>();
    Double total_amount = 0.00;

    public ExpenseDAO(Expense expense){

        addExpense(expense);

    }

    public void addExpense(Expense expense){

        expenses.add(expense);
        total_amount += expense.getValor();

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


    public Expense get(int pos){
        return expenses.get(pos);
    }

    public ArrayList<Expense> getAll(){
        return expenses;
    }

    public Double getTotal_amount(){
        return total_amount;
    }

}

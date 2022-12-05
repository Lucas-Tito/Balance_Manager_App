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
        total_amount += expense.getValue();

    }

    public void removeExpense(int id){

        total_amount -= expenses.get(id).getValue();
        expenses.remove(id);

    }

    public void updateExpense(Expense updatedExpense){

        expenses.get(updatedExpense.getId()).setValue(updatedExpense.getValue());
        expenses.get(updatedExpense.getId()).setIsPaid(updatedExpense.getIsPaid());
        expenses.get(updatedExpense.getId()).setDescription(updatedExpense.getDescription());
        expenses.get(updatedExpense.getId()).setCategory(updatedExpense.getCategory());
        expenses.get(updatedExpense.getId()).setLocation(updatedExpense.getLocation());

    }


    public Expense get(int pos){
        return expenses.get(pos);
    }

    public int getSize(){
        return expenses.size();
    }

    public ArrayList<Expense> getAll(){
        return expenses;
    }

    public Double getTotal_amount(){
        return total_amount;
    }

}

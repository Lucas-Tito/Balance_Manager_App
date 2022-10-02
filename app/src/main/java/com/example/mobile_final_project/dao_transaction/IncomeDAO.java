package com.example.mobile_final_project.dao_transaction;

import com.example.mobile_final_project.model.Income;

import java.io.Serializable;
import java.util.ArrayList;

public class IncomeDAO implements Serializable {

    ArrayList<Income> incomes = new ArrayList<>();
    Double total_amount = 0.00;

    public IncomeDAO(Income income){

        addIncome(income);

    }

    public void addIncome(Income income){

        incomes.add(income);
        total_amount += income.getValue();

    }

    public void removeExpense(int id){

        incomes.remove(id);

    }

    public void editExpense(int id, int amount, boolean isPaid, String description, String category){

        incomes.get(id).setValue(amount);
        incomes.get(id).setIsPaid(isPaid);
        incomes.get(id).setDescription(description);
        incomes.get(id).setCategory(category);

    }


    public Income get(int pos){
        return incomes.get(pos);
    }

    public int getSize(){
        return incomes.size();
    }

    public ArrayList<Income> getAll(){
        return incomes;
    }

    public Double getTotal_amount(){
        return total_amount;
    }


}

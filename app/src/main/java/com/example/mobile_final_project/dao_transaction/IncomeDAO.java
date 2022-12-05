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

    public void removeIncome(int id){

        total_amount -= incomes.get(id).getValue();
        incomes.remove(id);

    }

    public void updateIncome(Income updatedIncome){

        incomes.get(updatedIncome.getId()).setValue(updatedIncome.getValue());
        incomes.get(updatedIncome.getId()).setIsPaid(updatedIncome.getIsPaid());
        incomes.get(updatedIncome.getId()).setDescription(updatedIncome.getDescription());
        incomes.get(updatedIncome.getId()).setCategory(updatedIncome.getCategory());
        incomes.get(updatedIncome.getId()).setLocation(updatedIncome.getLocation());

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

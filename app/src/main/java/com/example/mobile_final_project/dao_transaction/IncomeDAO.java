package com.example.mobile_final_project.dao_transaction;

import com.example.mobile_final_project.model.Expense;
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
        total_amount += income.getValor();

    }

    public void removeExpense(int id){

        incomes.remove(id);

    }

    public void editExpense(int id, int amount, boolean isPaid, String description, String category){

        incomes.get(id).setValor(amount);
        incomes.get(id).setIsPaid(isPaid);
        incomes.get(id).setDescricao(description);
        incomes.get(id).setCategoria(category);

    }


    public Income get(int pos){
        return incomes.get(pos);
    }

    public ArrayList<Income> getAll(){
        return incomes;
    }

    public Double getTotal_amount(){
        return total_amount;
    }


}

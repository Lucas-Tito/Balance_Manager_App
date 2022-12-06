package com.example.mobile_final_project.factoty;

import static android.content.ContentValues.TAG;

import android.util.Log;

import com.example.mobile_final_project.dao_transaction.ExpenseDAO;
import com.example.mobile_final_project.model.Expense;
import com.example.mobile_final_project.model.Income;
import com.example.mobile_final_project.viewmodel.TransactionDBViewModel;

import java.util.HashMap;
import java.util.Map;

public class TransactionFactory implements ITransactionFactory
{
    public Map<String, Object> daoToTransactionDB(Object transaction)
    {
        Map<String, Object> transactionDB = new HashMap<>();
        if(transaction instanceof Expense)
        {
            transactionDB.put("categoty", ((Expense)transaction).getCategory().toString());
            transactionDB.put("description", ((Expense)transaction).getDescription().toString());
            transactionDB.put("id", ((Expense)transaction).getId());
            transactionDB.put("isPaid", ((Expense)transaction).getIsPaid());
            transactionDB.put("location", ((Expense)transaction).getLocation());
            transactionDB.put("type", "expense");
            transactionDB.put("value", ((Expense)transaction).getValue());
        }
        else if(transaction instanceof Income){
            Log.d(TAG, "Entrou no factory do income: "+ transaction);
            transactionDB.put("categoty", ((Income)transaction).getCategory().toString());
            transactionDB.put("description", ((Income)transaction).getDescription().toString());
            transactionDB.put("id", ((Income)transaction).getId());
            transactionDB.put("isPaid", ((Income)transaction).getIsPaid());
            transactionDB.put("location", ((Income)transaction).getLocation());
            transactionDB.put("type", "income");
            transactionDB.put("value", ((Income)transaction).getValue());
        }

        return transactionDB;
    }

}

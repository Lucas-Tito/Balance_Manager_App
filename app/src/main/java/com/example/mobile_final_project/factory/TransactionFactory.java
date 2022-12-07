package com.example.mobile_final_project.factory;

import static android.content.ContentValues.TAG;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import android.util.Log;

import com.example.mobile_final_project.model.Expense;
import com.example.mobile_final_project.model.Income;
import com.example.mobile_final_project.model.Transaction;
import com.example.mobile_final_project.viewmodel.TransactionDBViewModel;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TransactionFactory implements ITransactionFactory
{
    public Map<String, Object> daoToTransactionDB(Object transaction)
    {
        Map<String, Object> transactionDB = new HashMap<>();
        if(transaction instanceof Expense)
        {
            transactionDB.put("category", ((Expense)transaction).getCategory().toString());
            transactionDB.put("entryDate", ((Expense)transaction).getDate().toString());
            transactionDB.put("description", ((Expense)transaction).getDescription().toString());
            transactionDB.put("id", ((Expense)transaction).getId());
            transactionDB.put("isPaid", ((Expense)transaction).getIsPaid());
            transactionDB.put("location", ((Expense)transaction).getLocation());
            transactionDB.put("type", "expense");
            transactionDB.put("value", ((Expense)transaction).getValue());
        }
        else if(transaction instanceof Income){
            Log.d(TAG, "Entrou no factory do income: "+ transaction);
            transactionDB.put("category", ((Income)transaction).getCategory().toString());
            transactionDB.put("entryDate", ((Income)transaction).getDate().toString());
            transactionDB.put("description", ((Income)transaction).getDescription().toString());
            transactionDB.put("id", ((Income)transaction).getId());
            transactionDB.put("isPaid", ((Income)transaction).getIsPaid());
            transactionDB.put("location", ((Income)transaction).getLocation());
            transactionDB.put("type", "income");
            transactionDB.put("value", ((Income)transaction).getValue());
        }

        return transactionDB;
    }

    public Transaction transactionDBToDao(TransactionDBViewModel transaction)
    {
        SimpleDateFormat format = new SimpleDateFormat("EEE,dd MMM yyyy HH:mm:ss");
        Date date = new Date();
        try {
            date = format.parse(transaction.entryDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(transaction.type.equals("expense"))
        {
            Transaction expense = new Expense(transaction.id, transaction.description, transaction.category, transaction.location, date,transaction.value, transaction.isPaid );
            return  expense;
        }
        else{
            Transaction income = new Income(transaction.id, transaction.description, transaction.category, transaction.location, date,transaction.value, transaction.isPaid );
            return  income;
        }
    }

}

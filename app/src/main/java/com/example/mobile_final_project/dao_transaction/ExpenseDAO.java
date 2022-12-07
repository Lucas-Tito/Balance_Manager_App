package com.example.mobile_final_project.dao_transaction;

import static android.content.ContentValues.TAG;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.mobile_final_project.factory.ITransactionFactory;
import com.example.mobile_final_project.factory.TransactionFactory;
import com.example.mobile_final_project.model.Expense;
import com.example.mobile_final_project.model.Transaction;
import com.example.mobile_final_project.viewmodel.TransactionDBViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.Serializable;
import java.util.ArrayList;

//class implements Serializable so it can be passed from Activities to Fragments
public class ExpenseDAO implements Serializable {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ITransactionFactory transactionFactory = new TransactionFactory();
    ArrayList<Expense> expenses = new ArrayList<>();
    Double total_amount = 0.00;

    public ExpenseDAO(){}

    public ExpenseDAO(Expense expense){

        addExpense(expense);

    }


    public void getFromDB(){

        //buscar expenses no banco
        db.collection("transaction")
                .whereEqualTo("type","expense")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, "Mensagem de leitura no banco | "+ document.getId() + " => " + document.getData());
                                Gson gson = new GsonBuilder().create();

                                String transactionJson = gson.toJson(document.getData());
                                TransactionDBViewModel tdbModel = gson.fromJson(transactionJson, TransactionDBViewModel.class);
                                Log.d(TAG, "cu pode |> "+ transactionFactory.transactionDBToDao(tdbModel));

                                if(tdbModel.description.equals("cu"))
                                {
                                    Expense expense = (Expense) transactionFactory.transactionDBToDao(tdbModel);
                                    expenses.add(expense);
                                    Log.d(TAG, "for expenses getall |> "+ expense);

                                    Log.d(TAG, "Mensagem expense getall from json: "+ expense.toString());
                                }

                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
        //Log.d(TAG, " out expenses getall |> "+ expenses);

    }


    public void addExpense(Expense expense){

        expenses.add(expense);
        total_amount += expense.getValue();

        db.collection("transaction")
                .add(this.transactionFactory.daoToTransactionDB(expense))
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });

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

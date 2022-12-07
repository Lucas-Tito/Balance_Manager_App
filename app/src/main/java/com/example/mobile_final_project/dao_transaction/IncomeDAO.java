package com.example.mobile_final_project.dao_transaction;

import static android.content.ContentValues.TAG;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.mobile_final_project.factory.ITransactionFactory;
import com.example.mobile_final_project.factory.TransactionFactory;
import com.example.mobile_final_project.model.Expense;
import com.example.mobile_final_project.model.Income;
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

public class IncomeDAO implements Serializable {

    ArrayList<Income> incomes = new ArrayList<>();
    Double total_amount = 0.00;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ITransactionFactory transactionFactory = new TransactionFactory();


    public IncomeDAO(){}

    public IncomeDAO(Income income){

        addIncome(income);

    }


    public void getFromDB(){

        //buscar expenses no banco
        db.collection("transaction")
                .whereEqualTo("type","income")
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

                                if(transactionFactory.transactionDBToDao(tdbModel) instanceof Income)
                                {
                                    Income income = (Income) transactionFactory.transactionDBToDao(tdbModel);
                                    incomes.add(income);
                                    total_amount += income.getValue();
                                    Log.d(TAG, "for expenses getall |> "+ income);

                                    Log.d(TAG, "Mensagem expense getall from json: "+ income.toString());
                                }
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
        //Log.d(TAG, " out expenses getall |> "+ expenses);

    }

    public void addIncome(Income income){

        incomes.add(income);
        total_amount += income.getValue();
        db.collection("transaction")
                .add(this.transactionFactory.daoToTransactionDB(income))
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

        //buscar incomes no banco
//        db.collection("transaction")
//                .whereEqualTo("type","income")
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                Log.d(TAG, "Mensagem de leitura no banco | "+ document.getId() + " => " + document.getData());
//                                Gson gson = new GsonBuilder().create();
//                                String transactionJson = gson.toJson(document.getData());
//                                TransactionDBViewModel transactionDBViewModel =gson.fromJson(transactionJson, TransactionDBViewModel.class);
//                                Income income = gson.fromJson(transactionJson, Income.class);
//                                incomes.add(income);
//
//                                Log.d(TAG, "Mensagem transviewModel |> "+ transactionDBViewModel.toString());
//                                Log.d(TAG, "Mensagem income from json: "+ income.toString());
//                            }
//                        } else {
//                            Log.w(TAG, "Error getting documents.", task.getException());
//                        }
//                    }
//                });

        return incomes;
    }

    public Double getTotal_amount(){
        return total_amount;
    }


}

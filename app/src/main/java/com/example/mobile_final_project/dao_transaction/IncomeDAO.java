package com.example.mobile_final_project.dao_transaction;

import static android.content.ContentValues.TAG;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.mobile_final_project.factoty.ITransactionFactory;
import com.example.mobile_final_project.factoty.TransactionFactory;
import com.example.mobile_final_project.model.Income;
import com.example.mobile_final_project.viewmodel.TransactionDBViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
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
    //FirebaseFirestore db = FirebaseFirestore.getInstance();
    ITransactionFactory transactionFactory = new TransactionFactory();


    public IncomeDAO(){}




    public void getFromDB(FirebaseFirestore db, FirebaseUser user){

        Log.d(TAG, "email do user vindo da main "+user.getEmail());

       // buscar expenses no banco
        db.collection("transaction")
                .whereEqualTo("type","income")
                .whereEqualTo("userEmail",user.getEmail().toString())
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

    public void addIncome(Income income, FirebaseFirestore db){

        incomes.add(income);
        total_amount += income.getValue();

        Log.d(TAG, "this.transactionFactory.daoToTransactionDB(income) " + this.transactionFactory.daoToTransactionDB(income));

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

    public void removeIncome(int id, FirebaseFirestore db, FirebaseUser user){

        total_amount -= incomes.get(id).getValue();
        incomes.remove(id);

        db.collection("transaction")
                .whereEqualTo("id",id)
                .whereEqualTo("userEmail",user.getEmail().toString())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                //Log.d(TAG, "Mensagem de leitura no banco | "+ document.getId() + " => " + document.getData());
                                Gson gson = new GsonBuilder().create();

                                String transactionJson = gson.toJson(document.getData());
                                TransactionDBViewModel tdbModel = gson.fromJson(transactionJson, TransactionDBViewModel.class);
                                //Log.d(TAG, "cu pode |> "+ transactionFactory.transactionDBToDao(tdbModel));

                                System.out.println("sus " + tdbModel.type);
                                if(tdbModel.id == id && tdbModel.type.equals("income"))
                                {
                                    document.getReference().delete();
                                }
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });

    }

    public void updateIncome(Income updatedIncome, FirebaseFirestore db, FirebaseUser user){

        incomes.get(updatedIncome.getId()).setValue(updatedIncome.getValue());
        incomes.get(updatedIncome.getId()).setIsPaid(updatedIncome.getIsPaid());
        incomes.get(updatedIncome.getId()).setDescription(updatedIncome.getDescription());
        incomes.get(updatedIncome.getId()).setCategory(updatedIncome.getCategory());
        incomes.get(updatedIncome.getId()).setLocation(updatedIncome.getLocation());

        db.collection("transaction")
                .whereEqualTo("id", updatedIncome.getId())
                .whereEqualTo("userEmail",user.getEmail().toString())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                //Log.d(TAG, "Mensagem de leitura no banco | "+ document.getId() + " => " + document.getData());
                                Gson gson = new GsonBuilder().create();

                                String transactionJson = gson.toJson(document.getData());
                                TransactionDBViewModel tdbModel = gson.fromJson(transactionJson, TransactionDBViewModel.class);
                                //Log.d(TAG, "cu pode |> "+ transactionFactory.transactionDBToDao(tdbModel));

                                System.out.println("sus " + tdbModel.type);
                                if(tdbModel.id == updatedIncome.getId() && tdbModel.type.equals("income"))
                                {
                                    document.getReference().update("category", updatedIncome.getCategory());
                                    document.getReference().update("description", updatedIncome.getDescription());
                                    document.getReference().update("isPaid", updatedIncome.getIsPaid());
                                    document.getReference().update("location", updatedIncome.getLocation());
                                    document.getReference().update("value", updatedIncome.getValue());
                                }
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });

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

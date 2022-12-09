package com.example.mobile_final_project.dao_transaction;

import static android.content.ContentValues.TAG;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.mobile_final_project.factoty.ITransactionFactory;
import com.example.mobile_final_project.factoty.TransactionFactory;
import com.example.mobile_final_project.model.Expense;
import com.example.mobile_final_project.viewmodel.TransactionDBViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
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

    //FirebaseFirestore db = FirebaseFirestore.getInstance();
    ITransactionFactory transactionFactory = new TransactionFactory();
    ArrayList<Expense> expenses = new ArrayList<>();
    Double total_amount = 0.00;

    public ExpenseDAO(){}




    public interface FireStoreCallback{
        void onCallback();
    }
    public void getFromDB(FireStoreCallback fireStoreCallback, FirebaseFirestore db, FirebaseUser user){

        //buscar expenses no banco
        db.collection("transaction")
                .whereEqualTo("type","expense")
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

                                if(transactionFactory.transactionDBToDao(tdbModel) instanceof Expense)
                                {
                                    Expense expense = (Expense) transactionFactory.transactionDBToDao(tdbModel);
                                    expenses.add(expense);
                                    total_amount += expense.getValue();
                                    //Log.d(TAG, "for expenses getall |> "+ expense);

                                    //Log.d(TAG, "Mensagem expense getall from json: "+ expense.toString());
                                    fireStoreCallback.onCallback();
                                }
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
        //Log.d(TAG, " out expenses getall |> "+ expenses);

    }


    public void addExpense(Expense expense, FirebaseFirestore db){

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

    public void removeExpense(int id, FirebaseFirestore db, FirebaseUser user){

        total_amount -= expenses.get(id).getValue();
        expenses.remove(id);

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
                                if(tdbModel.id == id && tdbModel.type.equals("expense"))
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

    public void updateExpense(Expense updatedExpense, FirebaseFirestore db, FirebaseUser user){

        expenses.get(updatedExpense.getId()).setValue(updatedExpense.getValue());
        expenses.get(updatedExpense.getId()).setIsPaid(updatedExpense.getIsPaid());
        expenses.get(updatedExpense.getId()).setDescription(updatedExpense.getDescription());
        expenses.get(updatedExpense.getId()).setCategory(updatedExpense.getCategory());
        expenses.get(updatedExpense.getId()).setLocation(updatedExpense.getLocation());


        db.collection("transaction")
                .whereEqualTo("id", updatedExpense.getId())
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
                                if(tdbModel.id == updatedExpense.getId() && tdbModel.type.equals("expense"))
                                {
                                    document.getReference().update("category", updatedExpense.getCategory());
                                    document.getReference().update("description", updatedExpense.getDescription());
                                    document.getReference().update("isPaid", updatedExpense.getIsPaid());
                                    document.getReference().update("location", updatedExpense.getLocation());
                                    document.getReference().update("value", updatedExpense.getValue());
                                }
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });

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

package com.example.mobile_final_project;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mobile_final_project.Adapters.Adapter_RecyclerView;
import com.example.mobile_final_project.Adapters.Interface_RecyclerView;
import com.example.mobile_final_project.dao_transaction.ExpenseDAO;
import com.example.mobile_final_project.model.Despesa;

import java.util.ArrayList;


public class Transactions extends Fragment implements Interface_RecyclerView {

    private static final String expenseDao_KEY = "expenseDao_key";
    private ExpenseDAO expenseDAO;

    public Transactions() {
        // Required empty public constructor
    }


    public static Transactions newInstance(ExpenseDAO expenseDAO) {
        Transactions fragment = new Transactions();
        Bundle args = new Bundle();
        //used to receive expenseDao object when called
        args.putSerializable(expenseDao_KEY, expenseDAO);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            //initialize expenseDao object when Transactions.NewInstance is called
            expenseDAO = (ExpenseDAO) getArguments().getSerializable(expenseDao_KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.bottom_nav_transactions, container, false);

        System.out.println("AMogus2" + expenseDAO.get(0).getValor());
        build_recyclerView(v);

        return v;
    }

    private void build_recyclerView(View v) {

        ArrayList<Despesa> expenses = expenseDAO.getAll();

        androidx.recyclerview.widget.RecyclerView recyclerView;
        recyclerView = v.findViewById(R.id.recyclerView);

        Adapter_RecyclerView myAdapter = new Adapter_RecyclerView(getActivity(), expenses, this);
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

    }

    //click listener for recycleView items
    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(getActivity(), Edit_Transaction_Activity.class);
        intent.putExtra("expenseDao", this.expenseDAO);
        startActivity(intent);
    }
}
package com.example.mobile_final_project;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mobile_final_project.Adapters.Adapter_RecyclerView;
import com.example.mobile_final_project.Adapters.Interface_RecyclerView;
import com.example.mobile_final_project.dao_transaction.ExpenseDAO;
import com.example.mobile_final_project.dao_transaction.IncomeDAO;
import com.example.mobile_final_project.model.Expense;
import com.example.mobile_final_project.model.Income;
import com.example.mobile_final_project.model.Transaction;

import java.util.ArrayList;


public class Transactions_List extends Fragment implements Interface_RecyclerView {

    private static final String expenseDao_KEY = "expenseDao_key";
    private static final String incomeDao_KEY = "incomeDao_key";
    private ExpenseDAO expenseDAO;
    private IncomeDAO incomeDAO;

    public Transactions_List() {
        // Required empty public constructor
    }



    public static Transactions_List newInstance(ExpenseDAO expenseDAO, IncomeDAO incomeDAO) {
        Transactions_List fragment = new Transactions_List();
        Bundle args = new Bundle();
        //used to receive expenseDao object when called
        args.putSerializable(expenseDao_KEY, expenseDAO);
        args.putSerializable(incomeDao_KEY, incomeDAO);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            //initialize expenseDao object when Transactions.NewInstance is called
            expenseDAO = (ExpenseDAO) getArguments().getSerializable(expenseDao_KEY);
            incomeDAO = (IncomeDAO) getArguments().getSerializable(incomeDao_KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.bottom_nav_transactions, container, false);

        build_recyclerView(v);

        return v;
    }


    private ArrayList<Transaction> united_transactions = new ArrayList<>();
    private void build_recyclerView(View v) {

        united_transactions.addAll(expenseDAO.getAll());
        united_transactions.addAll(incomeDAO.getAll());

        androidx.recyclerview.widget.RecyclerView recyclerView;
        recyclerView = v.findViewById(R.id.recyclerView);

        Adapter_RecyclerView myAdapter = new Adapter_RecyclerView(getActivity(), united_transactions, this);
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

    }

    //click listener for recycleView items
    @Override
    public void onItemClick(int position, String fragToStart) {
        Intent intent = new Intent(getActivity(), Edit_Transaction_Activity.class);
        intent.putExtra("transaction_pos", position);
        intent.putExtra("expenseDao", this.expenseDAO);
        intent.putExtra("incomeDao", this.incomeDAO);

        if(fragToStart.equals("editExpense")){
            intent.putExtra("frag_key", fragToStart);
            getActivity().startActivityForResult(intent, 101);
        }
        else{
            intent.putExtra("frag_key", fragToStart);
            getActivity().startActivityForResult(intent, 102);
        }


    }
}
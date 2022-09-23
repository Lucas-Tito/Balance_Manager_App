package com.example.mobile_final_project;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mobile_final_project.Adapters.Adapter_RecyclerView;
import com.example.mobile_final_project.dao_transaction.ExpenseDAO;
import com.example.mobile_final_project.model.Despesa;

import java.util.ArrayList;


public class Transactions extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Transactions() {
        // Required empty public constructor
    }


    public static Transactions newInstance(String param1, String param2) {
        Transactions fragment = new Transactions();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.bottom_nav_transactions, container, false);

        build_recyclerView(v);

        return v;
    }

    private void build_recyclerView(View v) {

        ExpenseDAO expenseDAO = new ExpenseDAO();

        ArrayList<Despesa> expenses = expenseDAO.getAll();

        androidx.recyclerview.widget.RecyclerView recyclerView;
        recyclerView = v.findViewById(R.id.recyclerView);

        Adapter_RecyclerView myAdapter = new Adapter_RecyclerView(getActivity(), expenses);
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

    }
}
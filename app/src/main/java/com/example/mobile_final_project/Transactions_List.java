package com.example.mobile_final_project;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.example.mobile_final_project.Adapters.Transactions_Adapter_RecyclerView;
import com.example.mobile_final_project.Adapters.IRecyclerView_Transactions;
import com.example.mobile_final_project.dao_transaction.ExpenseDAO;
import com.example.mobile_final_project.dao_transaction.IncomeDAO;
import com.example.mobile_final_project.model.Transaction;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;


public class Transactions_List extends Fragment implements IRecyclerView_Transactions {

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

        build_labels(v);
        build_transactions_dropdown(v);
        build_recyclerView(v);



        return v;
    }

    public void build_labels(View v){

        //Removes currency symbol from string to prevent error parsing to double
        String str_incomes_amount = incomeDAO.getTotal_amount().toString();
        String str_expenses_amount = expenseDAO.getTotal_amount().toString();

        Double total_amount_value = Double.valueOf(Math.round(Double.parseDouble(str_incomes_amount.replace(getText(R.string.currency), ""))
                - Double.parseDouble(str_expenses_amount.replace(getText(R.string.currency), ""))));

        TextView current_balance_label = v.findViewById(R.id.current_balance_amount);
        current_balance_label.setText(getText(R.string.currency) + total_amount_value.toString());

    }


    private void build_transactions_dropdown(View v) {

        LinearLayout transactions_dropdown = v.findViewById(R.id.transactions_dropdown);

        transactions_dropdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(getActivity(), transactions_dropdown);
                popupMenu.getMenuInflater().inflate(R.menu.transactions_dropdown, popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {

                        switch (menuItem.getItemId()){

                            case R.id.transactions:
                                recyclerView_filter_Transactions();
                                break;

                            case R.id.expenses:
                                recyclerView_filter_Expenses();
                                break;

                            case R.id.incomes:
                                recyclerView_filter_Incomes();
                                break;

                        }

                        return true;
                    }
                });

                popupMenu.show();
            }
        });

    }


    androidx.recyclerview.widget.RecyclerView recyclerView;
    private void build_recyclerView(View v) {

        recyclerView = v.findViewById(R.id.recyclerView);
        recyclerView_filter_Transactions();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

    }


    private void recyclerView_filter_Expenses(){
        ArrayList<Transaction> united_transactions = new ArrayList<>();
        united_transactions.addAll(expenseDAO.getAll());
        Transactions_Adapter_RecyclerView myAdapter = new Transactions_Adapter_RecyclerView(getActivity(), united_transactions, this);
        recyclerView.setAdapter(myAdapter);
    }


    private void recyclerView_filter_Incomes(){
        ArrayList<Transaction> united_transactions = new ArrayList<>();
        united_transactions.addAll(incomeDAO.getAll());
        Transactions_Adapter_RecyclerView myAdapter = new Transactions_Adapter_RecyclerView(getActivity(), united_transactions, this);
        recyclerView.setAdapter(myAdapter);
    }


    private void recyclerView_filter_Transactions(){
        ArrayList<Transaction> united_transactions = new ArrayList<>();
        united_transactions.addAll(expenseDAO.getAll());
        united_transactions.addAll(incomeDAO.getAll());
        Transactions_Adapter_RecyclerView myAdapter = new Transactions_Adapter_RecyclerView(getActivity(), united_transactions, this);
        recyclerView.setAdapter(myAdapter);
    }



    //click listener for recycleView items
    @Override
    public void onItemClick(int position, String fragToStart) {
        Intent intent = new Intent(getActivity(), Edit_Transaction_Activity.class);
        intent.putExtra("transaction_pos", position);
        intent.putExtra("frag_key", fragToStart);



        if(fragToStart.equals("editExpense")){
            intent.putExtra("expenseToEdit", this.expenseDAO.get(position));
            getActivity().startActivityForResult(intent, 103);
        }
        else{
            intent.putExtra("incomeToEdit", this.incomeDAO.get(position));
            getActivity().startActivityForResult(intent, 104);
        }


    }
}
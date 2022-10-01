package com.example.mobile_final_project;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class Home extends Fragment {

    private static final String expenses_amount_KEY = "expenses_amount_key";

    private String str_expenses_amount;

    public Home() {
        // Required empty public constructor
    }


    public static Home newInstance(String expenses_amount) {
        Home fragment = new Home();
        Bundle args = new Bundle();
        args.putString(expenses_amount_KEY, expenses_amount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            str_expenses_amount = getArguments().getString(expenses_amount_KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.bottom_nav_home, container, false);

        build_balance_dashboard(v);

        return v;
    }


    private void build_balance_dashboard(View v){

        TextView expenses_amount = v.findViewById(R.id.expenses_amount);
        expenses_amount.setText(getText(R.string.currency) + str_expenses_amount);


    }
}
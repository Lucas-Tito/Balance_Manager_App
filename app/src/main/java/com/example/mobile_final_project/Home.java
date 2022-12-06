package com.example.mobile_final_project;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

public class Home extends Fragment {

    private static final String incomes_amount_KEY = "incomes_amount_key";
    private static final String expenses_amount_KEY = "expenses_amount_key";

    private String str_incomes_amount;
    private String str_expenses_amount;

    public Home() {
        // Required empty public constructor
    }


    public static Home newInstance(String expenses_amount, String incomes_amount) {
        Home fragment = new Home();
        Bundle args = new Bundle();
        args.putString(incomes_amount_KEY, incomes_amount);
        args.putString(expenses_amount_KEY, expenses_amount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            str_incomes_amount = getArguments().getString(incomes_amount_KEY);
            str_expenses_amount = getArguments().getString(expenses_amount_KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.bottom_nav_home, container, false);

        build_hide_btn(v);

        return v;
    }




    @Override
    public void onResume() {
        super.onResume();

        build_balance_dashboard(getView());
    }

    boolean visible = true;
    private void build_hide_btn(View v) {

        ImageView hide_btn = v.findViewById(R.id.hide_btn);
        TextView total_amount = v.findViewById(R.id.total_amount);
        TextView incomes_amount = v.findViewById(R.id.incomes_amount);
        TextView expenses_amount = v.findViewById(R.id.expenses_amount);


        hide_btn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NewApi")
            @Override
            public void onClick(View view) {

                if(visible){
                    total_amount.setForeground(new ColorDrawable(getResources().getColor(R.color.black)));
                    incomes_amount.setForeground(new ColorDrawable(getResources().getColor(R.color.black)));
                    expenses_amount.setForeground(new ColorDrawable(getResources().getColor(R.color.black)));
                    hide_btn.setImageResource(R.drawable.home_invisibility_ic);
                    visible = false;
                }
                else{
                    total_amount.setForeground(null);
                    incomes_amount.setForeground(null);
                    expenses_amount.setForeground(null);
                    hide_btn.setImageResource(R.drawable.home_visibility_ic);
                    visible = true;
                }

            }
        });

    }


    private void build_balance_dashboard(View v){

        TextView incomes_amount = v.findViewById(R.id.incomes_amount);
        incomes_amount.setText(getText(R.string.currency) + str_incomes_amount);
        TextView expenses_amount = v.findViewById(R.id.expenses_amount);
        expenses_amount.setText(getText(R.string.currency) + str_expenses_amount);

        //Removes currency symbol from string to prevent error parsing to double
        Double total_amount_value = Double.valueOf(Math.round(Double.parseDouble(str_incomes_amount.replace(getText(R.string.currency), ""))
                - Double.parseDouble(str_expenses_amount.replace(getText(R.string.currency), ""))));

        TextView total_amount = v.findViewById(R.id.total_amount);
        total_amount.setText(getText(R.string.currency) + total_amount_value.toString());
    }
}
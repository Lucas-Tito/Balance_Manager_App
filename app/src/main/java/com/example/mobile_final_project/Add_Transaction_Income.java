package com.example.mobile_final_project;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;

import com.example.mobile_final_project.dao_transaction.ExpenseDAO;
import com.example.mobile_final_project.dao_transaction.IncomeDAO;
import com.example.mobile_final_project.model.Income;
import com.example.mobile_final_project.utils.EditAmountTransaction;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Date;


public class Add_Transaction_Income extends Fragment implements DialogCloseListener {

    private static final String incomeDAO_KEY = "incomeDAO_KEY";
    private IncomeDAO incomeDAO;

    //private static DecimalFormat REAL_FORMATTER = new DecimalFormat("0.##");

    DecimalFormatSymbols symbols = new DecimalFormatSymbols();


    private static DecimalFormat REAL_FORMATTER = new DecimalFormat("$ #,##0.00");

    //Componentes do layout
    private TextView incomeAmount;

    public Add_Transaction_Income() {
        // Required empty public constructor
    }

    public static Add_Transaction_Income newInstance(IncomeDAO incomeDAO) {
        Add_Transaction_Income fragment = new Add_Transaction_Income();
        Bundle args = new Bundle();
        args.putSerializable(incomeDAO_KEY, incomeDAO);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            incomeDAO = (IncomeDAO) getArguments().getSerializable(incomeDAO_KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.add_transaction_income, container, false);

        build_confirm_btn(v);

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        incomeAmount = getView().findViewById(R.id.amount);

        incomeAmount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new EditAmountTransaction().show(getActivity().getSupportFragmentManager(), EditAmountTransaction.newInstance().getTag());
            }
        });

    }

    public void build_confirm_btn(View v){

        ImageButton confirm_btn = v.findViewById(R.id.confirm_btn);
        Switch isPaid_switch = v.findViewById(R.id.isPaid_switch);
        EditText description_label = v.findViewById(R.id.description);

        confirm_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Removes currency symbol from string to prevent error parsing to double
                String str_amount = incomeAmount.getText().toString().replace(getText(R.string.currency), "");

                Double amount = Double.parseDouble(str_amount);
                boolean isPaid = isPaid_switch.isChecked();
                String description = description_label.getText().toString();

                Income newIncome = new Income(incomeDAO.getSize(), description, new Date(), amount, isPaid);
                incomeDAO.addIncome(newIncome);

                Intent returnIntent = new Intent();
                returnIntent.putExtra("newIncomeDao", incomeDAO);
                getActivity().setResult(getActivity().RESULT_OK, returnIntent);
                getActivity().finish();
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();



    }

    @Override
    public void handleDialogClose(DialogInterface dialog)
    {

    }



}
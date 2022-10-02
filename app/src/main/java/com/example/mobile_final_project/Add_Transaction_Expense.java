package com.example.mobile_final_project;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;

import com.example.mobile_final_project.dao_transaction.ExpenseDAO;
import com.example.mobile_final_project.model.Expense;

import java.util.Date;


public class Add_Transaction_Expense extends Fragment {

    private static final String expenseDAO_KEY = "expenseDAO_KEY";
    private ExpenseDAO expenseDAO;

    public Add_Transaction_Expense() {
        // Required empty public constructor
    }


    public static Add_Transaction_Expense newInstance(ExpenseDAO expenseDAO) {
        Add_Transaction_Expense fragment = new Add_Transaction_Expense();
        Bundle args = new Bundle();
        args.putSerializable(expenseDAO_KEY, expenseDAO);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            expenseDAO = (ExpenseDAO) getArguments().getSerializable(expenseDAO_KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.add_transaction_expense, container, false);

        build_confirm_btn(v);

        return v;
    }

    public void build_confirm_btn(View v){

        ImageButton confirm_btn = v.findViewById(R.id.confirm_btn);
        TextView label_amount = v.findViewById(R.id.amount);
        Switch isPaid_switch = v.findViewById(R.id.isPaid_switch);
        EditText description_label = v.findViewById(R.id.description);

        confirm_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Removes currency symbol from string to prevent error parsing to double
                String str_amount = label_amount.getText().toString().replace(getText(R.string.currency), "");

                Double amount = Double.parseDouble(str_amount);
                boolean isPaid = isPaid_switch.isChecked();
                String description = description_label.getText().toString();

                Expense newExpense = new Expense(expenseDAO.getSize(), description, new Date(), amount, isPaid);
                expenseDAO.addExpense(newExpense);

                Intent returnIntent = new Intent();
                returnIntent.putExtra("newExpenseDao", expenseDAO);
                getActivity().setResult(getActivity().RESULT_OK, returnIntent);
                getActivity().finish();
            }
        });

    }

}
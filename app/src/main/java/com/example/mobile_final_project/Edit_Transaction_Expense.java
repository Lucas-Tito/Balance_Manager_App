package com.example.mobile_final_project;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;

import com.example.mobile_final_project.dao_transaction.ExpenseDAO;


public class Edit_Transaction_Expense extends Fragment {

    private static final String expenseDao_KEY = "expenseDao_key";
    private static final String transaction_pos_KEY = "transaction_pos_key";
    private ExpenseDAO expenseDao;
    private int transaction_pos;

    TextView amount;
    Switch isPaid_switch;
    AppCompatEditText description;


    public Edit_Transaction_Expense() {
        // Required empty public constructor
    }


    public static Edit_Transaction_Expense newInstance(ExpenseDAO expenseDAO, int transaction_pos) {
        Edit_Transaction_Expense fragment = new Edit_Transaction_Expense();
        Bundle args = new Bundle();
        args.putSerializable(expenseDao_KEY, expenseDAO);
        args.putSerializable(transaction_pos_KEY, transaction_pos);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            //initialize expenseDao object when Edit_Transaction_Expenses.NewInstance is called
            expenseDao = (ExpenseDAO) getArguments().getSerializable(expenseDao_KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.edit_transaction_expense, container, false);

        amount = v.findViewById(R.id.amount);
        isPaid_switch = v.findViewById(R.id.isPaid_switch);
        description = v.findViewById(R.id.description);

        buildView(transaction_pos);
        build_confirm_btn(v, transaction_pos);

        return v;

    }

    private void buildView(int expensePos){

        amount.setText(getString(R.string.currency) + Double.toString(expenseDao.get(expensePos).getValue()));
        isPaid_switch.setChecked(expenseDao.get(expensePos).getIsPaid());
        description.setText(expenseDao.get(expensePos).getDescription());

    }

    private void build_confirm_btn(View v, int expensePos){

        ImageButton confirm_btn = v.findViewById(R.id.confirm_btn);

        confirm_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Removes currency symbol from string to prevent error parsing to double
                String str_amount = amount.getText().toString().replace(getText(R.string.currency), "");

                expenseDao.get(expensePos).setValue(Double.parseDouble(str_amount));
                expenseDao.get(expensePos).setIsPaid(isPaid_switch.isChecked());
                expenseDao.get(expensePos).setDescription(description.getText().toString());

                Intent returnIntent = new Intent();
                returnIntent.putExtra("newExpenseDao", expenseDao);
                getActivity().setResult(getActivity().RESULT_OK, returnIntent);
                getActivity().finish();
            }
        });

    }
}
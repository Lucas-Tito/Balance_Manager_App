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

import com.example.mobile_final_project.dao_transaction.IncomeDAO;


public class Edit_Transaction_Income extends Fragment {

    private static final String incomeDao_KEY = "expenseDao_key";
    private static final String transaction_pos_KEY = "transaction_pos_key";
    private IncomeDAO incomeDao;
    private int transaction_pos;

    TextView amount;
    Switch isReceived_switch;
    AppCompatEditText description;


    public Edit_Transaction_Income() {
        // Required empty public constructor
    }


    public static Edit_Transaction_Income newInstance(IncomeDAO incomeDAO, int transaction_pos) {
        Edit_Transaction_Income fragment = new Edit_Transaction_Income();
        Bundle args = new Bundle();
        args.putSerializable(incomeDao_KEY, incomeDAO);
        args.putSerializable(transaction_pos_KEY, transaction_pos);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            incomeDao = (IncomeDAO) getArguments().getSerializable(incomeDao_KEY);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.edit_transaction_income, container, false);

        amount = v.findViewById(R.id.amount);
        isReceived_switch = v.findViewById(R.id.isReceived_switch);
        description = v.findViewById(R.id.description);

        buildView(transaction_pos);
        build_confirm_btn(v, transaction_pos);

        return v;

    }

    private void buildView(int expensePos){

        amount.setText(getString(R.string.currency) + Double.toString(incomeDao.get(expensePos).getValue()));
        isReceived_switch.setChecked(incomeDao.get(expensePos).getIsPaid());
        description.setText(incomeDao.get(expensePos).getDescription());

    }

    private void build_confirm_btn(View v, int incomePos){

        ImageButton confirm_btn = v.findViewById(R.id.confirm_btn);

        confirm_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Removes currency symbol from string to prevent error parsing to double
                String str_amount = amount.getText().toString().replace(getText(R.string.currency), "");

                incomeDao.get(incomePos).setValue(Double.parseDouble(str_amount));
                incomeDao.get(incomePos).setIsPaid(isReceived_switch.isChecked());
                incomeDao.get(incomePos).setDescription(description.getText().toString());

                Intent returnIntent = new Intent();
                returnIntent.putExtra("newIncomeDao", incomeDao);
                getActivity().setResult(getActivity().RESULT_OK, returnIntent);
                getActivity().finish();
            }
        });

    }

}
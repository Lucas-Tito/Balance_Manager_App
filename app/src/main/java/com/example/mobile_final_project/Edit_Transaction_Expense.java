package com.example.mobile_final_project;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.SwitchCompat;
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
    ExpenseDAO expenseDao;

    TextView amount;
    Switch isPaid_switch;
    AppCompatEditText description;


    public Edit_Transaction_Expense() {
        // Required empty public constructor
    }


    public static Edit_Transaction_Expense newInstance(ExpenseDAO expenseDAO) {
        Edit_Transaction_Expense fragment = new Edit_Transaction_Expense();
        Bundle args = new Bundle();
        args.getSerializable(expenseDao_KEY);
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

        //receive expensePos from Edit_Transaction_Activity
        int expensePos = getArguments().getInt("expensePos");

        amount = v.findViewById(R.id.amount);
        isPaid_switch = v.findViewById(R.id.isPaid_switch);
        description = v.findViewById(R.id.description);

        buildView(v, expensePos);
        build_confirm_btn(v, expensePos);

        return v;
    }

    private void buildView(View v, int expensePos){

        amount.setText(Double.toString(expenseDao.get(expensePos).getValor()));
        isPaid_switch.setChecked(expenseDao.get(expensePos).getIsPaid());
        description.setText(expenseDao.get(expensePos).getDescricao());

    }

    private void build_confirm_btn(View v, int expensePos){

        ImageButton confirm_btn = v.findViewById(R.id.confirm_btn);

        confirm_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                expenseDao.get(expensePos).setValor(Double.parseDouble(amount.getText().toString()));
                expenseDao.get(expensePos).setIsPaid(isPaid_switch.isChecked());
                expenseDao.get(expensePos).setDescricao(description.getText().toString());
                getActivity().finish();
            }
        });



    }
}
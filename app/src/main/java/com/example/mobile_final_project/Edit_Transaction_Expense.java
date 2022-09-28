package com.example.mobile_final_project;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import com.example.mobile_final_project.dao_transaction.ExpenseDAO;


public class Edit_Transaction_Expense extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    ExpenseDAO expenseDao = new ExpenseDAO();

    private String mParam1;
    private String mParam2;

    public Edit_Transaction_Expense() {
        // Required empty public constructor
    }


    public static Edit_Transaction_Expense newInstance(String param1, String param2) {
        Edit_Transaction_Expense fragment = new Edit_Transaction_Expense();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.edit_transaction_expense, container, false);

        //receive expensePos from Edit_Transaction_Activity
        int expensePos = getArguments().getInt("expensePos");

        buildView(v, expensePos);
        build_confirm_btn(v, expensePos);

        return v;
    }


    private void buildView(View v, int expensePos){

        TextView amount = v.findViewById(R.id.amount);
        amount.setText(Double.toString(expenseDao.get(expensePos).getValor()));

        Switch isPaid_switch = v.findViewById(R.id.isPaid_switch);
        isPaid_switch.setChecked(expenseDao.get(expensePos).getIsPaid());

        AppCompatEditText description = v.findViewById(R.id.description);
        description.setText(expenseDao.get(expensePos).getDescricao());

    }

    private void build_confirm_btn(View v, int expensePos){



    }
}
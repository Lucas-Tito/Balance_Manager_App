package com.example.mobile_final_project;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.example.mobile_final_project.Adapters.Categories_Adapter_RecyclerView;
import com.example.mobile_final_project.Adapters.IRecyclerView_Categories;
import com.example.mobile_final_project.Adapters.IRecyclerView_Transactions;
import com.example.mobile_final_project.model.Expense;
import com.example.mobile_final_project.utils.CategoryChooser;
import com.example.mobile_final_project.utils.EditAmountTransaction;

import java.text.DecimalFormat;
import java.util.Date;


public class Add_Transaction_Expense extends Fragment {

    private static final String newExpenseID_KEY = "newExpenseID_KEY";
    private int newExpenseID;


    private static DecimalFormat REAL_FORMATTER = new DecimalFormat("$ #,##0.00");

    //Componentes do layout
    private TextView expenseAmount;

    public Add_Transaction_Expense() {
        // Required empty public constructor
    }


    public static Add_Transaction_Expense newInstance(int newExpenseID) {
        Add_Transaction_Expense fragment = new Add_Transaction_Expense();
        Bundle args = new Bundle();
        args.putInt(newExpenseID_KEY, newExpenseID);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            newExpenseID = getArguments().getInt(newExpenseID_KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.add_transaction_expense, container, false);

        build_choose_categories(v);
        build_confirm_btn(v);

        return v;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        expenseAmount = getView().findViewById(R.id.amount);

        expenseAmount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new EditAmountTransaction().show(getActivity().getSupportFragmentManager(), EditAmountTransaction.newInstance().getTag());
            }
        });

    }



    private void build_choose_categories(View v) {
        LinearLayout categories_btn = v.findViewById(R.id.category_layout);

        categories_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CategoryChooser categoryChooser = new CategoryChooser();
                categoryChooser.show(getActivity().getSupportFragmentManager(), "TAG");
            }
        });
    }




    public void build_confirm_btn(View v){

        ImageButton confirm_btn = v.findViewById(R.id.confirm_btn);
        TextView label_amount = v.findViewById(R.id.amount);
        Switch isPaid_switch = v.findViewById(R.id.isPaid_switch);
        EditText description_label = v.findViewById(R.id.description);
        TextView category_label = v.findViewById(R.id.category);

        confirm_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Removes currency symbol from string to prevent error parsing to double
                String str_amount = label_amount.getText().toString().replace(getText(R.string.currency), "");

                Double amount = Double.parseDouble(str_amount);
                boolean isPaid = isPaid_switch.isChecked();
                String description = description_label.getText().toString();
                String category = category_label.getText().toString();

                Expense newExpense = new Expense(newExpenseID, description, category, new Date(), amount, isPaid);

                Intent returnIntent = new Intent();
                returnIntent.putExtra("newExpense", newExpense);
                getActivity().setResult(getActivity().RESULT_OK, returnIntent);
                getActivity().finish();
            }
        });

    }

}
package com.example.mobile_final_project;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatEditText;
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


public class Edit_Transaction_Expense extends Fragment {

    private static final String expense_KEY = "expense_key";
    private static final String transaction_pos_KEY = "transaction_pos_key";
    private Expense expenseToEdit;
    private int transaction_pos;

    TextView amount;
    Switch isPaid_switch;
    AppCompatEditText description_field;
    TextView category_field;
    EditText location_field;


    public Edit_Transaction_Expense() {
        // Required empty public constructor
    }


    public static Edit_Transaction_Expense newInstance(Expense expenseToEdit, int transaction_pos) {
        Edit_Transaction_Expense fragment = new Edit_Transaction_Expense();
        Bundle args = new Bundle();
        args.putSerializable(expense_KEY, expenseToEdit);
        args.putSerializable(transaction_pos_KEY, transaction_pos);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            //initialize expenseDao object when Edit_Transaction_Expenses.NewInstance is called
            expenseToEdit = (Expense) getArguments().getSerializable(expense_KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.edit_transaction_expense, container, false);

        amount = v.findViewById(R.id.amount);
        isPaid_switch = v.findViewById(R.id.isPaid_switch);
        description_field = v.findViewById(R.id.description);
        category_field = v.findViewById(R.id.category);
        location_field = v.findViewById(R.id.location);

        buildView(transaction_pos);
        build_confirm_btn(v, transaction_pos);

        return v;

    }

    private void buildView(int expensePos){

        amount.setText(getString(R.string.currency) + expenseToEdit.getValue());
        isPaid_switch.setChecked(expenseToEdit.getIsPaid());
        description_field.setText(expenseToEdit.getDescription());
        category_field.setText(expenseToEdit.getCategory());
        location_field.setText(expenseToEdit.getLocation());

    }

    private void build_confirm_btn(View v, int expensePos){

        ImageButton confirm_btn = v.findViewById(R.id.confirm_btn);

        confirm_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Removes currency symbol from string to prevent error parsing to double
                String str_amount = amount.getText().toString().replace(getText(R.string.currency), "");

                expenseToEdit.setValue(Double.parseDouble(str_amount));
                expenseToEdit.setIsPaid(isPaid_switch.isChecked());
                expenseToEdit.setDescription(description_field.getText().toString());
                expenseToEdit.setCategory(category_field.getText().toString());
                expenseToEdit.setLocation(location_field.getText().toString());

                Intent returnIntent = new Intent();
                returnIntent.putExtra("updatedExpense", expenseToEdit);
                getActivity().setResult(getActivity().RESULT_OK, returnIntent);
                getActivity().finish();
            }
        });

    }
}
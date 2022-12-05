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

import com.example.mobile_final_project.dao_transaction.IncomeDAO;
import com.example.mobile_final_project.model.Income;


public class Edit_Transaction_Income extends Fragment {

    private static final String income_KEY = "expenseDao_key";
    private static final String transaction_pos_KEY = "transaction_pos_key";
    private Income incomeToEdit;
    private int transaction_pos;

    TextView amount;
    Switch isReceived_switch;
    AppCompatEditText description_field;
    TextView category_field;
    EditText location_field;


    public Edit_Transaction_Income() {
        // Required empty public constructor
    }


    public static Edit_Transaction_Income newInstance(Income incomeToEdit, int transaction_pos) {
        Edit_Transaction_Income fragment = new Edit_Transaction_Income();
        Bundle args = new Bundle();
        args.putSerializable(income_KEY, incomeToEdit);
        args.putSerializable(transaction_pos_KEY, transaction_pos);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            incomeToEdit = (Income) getArguments().getSerializable(income_KEY);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.edit_transaction_income, container, false);

        amount = v.findViewById(R.id.amount);
        isReceived_switch = v.findViewById(R.id.isReceived_switch);
        description_field = v.findViewById(R.id.description);
        category_field = v.findViewById(R.id.category);
        location_field = v.findViewById(R.id.location);

        buildView(transaction_pos);
        build_confirm_btn(v, transaction_pos);

        return v;

    }

    private void buildView(int incomePos){

        amount.setText(getString(R.string.currency) + incomeToEdit.getValue());
        isReceived_switch.setChecked(incomeToEdit.getIsPaid());
        description_field.setText(incomeToEdit.getDescription());
        category_field.setText(incomeToEdit.getCategory());
        location_field.setText(incomeToEdit.getLocation());

    }

    private void build_confirm_btn(View v, int incomePos){

        ImageButton confirm_btn = v.findViewById(R.id.confirm_btn);

        confirm_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Removes currency symbol from string to prevent error parsing to double
                String str_amount = amount.getText().toString().replace(getText(R.string.currency), "");

                incomeToEdit.setValue(Double.parseDouble(str_amount));
                incomeToEdit.setIsPaid(isReceived_switch.isChecked());
                incomeToEdit.setDescription(description_field.getText().toString());
                incomeToEdit.setCategory(category_field.getText().toString());
                incomeToEdit.setLocation(location_field.getText().toString());

                Intent returnIntent = new Intent();
                returnIntent.putExtra("updatedIncome", incomeToEdit);
                getActivity().setResult(getActivity().RESULT_OK, returnIntent);
                getActivity().finish();
            }
        });

    }

}
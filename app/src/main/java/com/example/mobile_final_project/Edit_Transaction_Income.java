package com.example.mobile_final_project;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mobile_final_project.dao_transaction.IncomeDAO;
import com.example.mobile_final_project.model.Income;
import com.example.mobile_final_project.utils.CategoryChooser;
import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import java.util.Arrays;
import java.util.List;


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
        build_choose_category(v);
        build_choose_location(v);
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



    private void build_choose_category(View v) {

        LinearLayout categories_btn = v.findViewById(R.id.category_layout);

        categories_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CategoryChooser categoryChooser = new CategoryChooser();
                categoryChooser.show(getActivity().getSupportFragmentManager(), "TAG");
            }
        });

    }


    private void build_choose_location(View v) {

        Places.initialize(getContext(), "AIzaSyBFLwrY0zRtVMU01jW4nvoDKTKZWVaO-DU");
        location_field = v.findViewById(R.id.location);
        location_field.setFocusable(false);
        location_field.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Place.Field> fieldList = Arrays.asList(Place.Field.ADDRESS, Place.Field.LAT_LNG,
                        Place.Field.NAME);
                Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fieldList).build(getActivity());
                startActivityForResult(intent, 10);
            }
        });

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 10 && resultCode == RESULT_OK){
            Place place = Autocomplete.getPlaceFromIntent(data);
            location_field.setText(place.getAddress());
            /*you can get locality name using place.getName()*/
        }
        else if(resultCode == AutocompleteActivity.RESULT_ERROR){
            Status status = Autocomplete.getStatusFromIntent(data);
            Toast.makeText(getContext(), status.getStatusMessage(), Toast.LENGTH_LONG).show();
        }
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
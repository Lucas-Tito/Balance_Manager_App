package com.example.mobile_final_project;

import static android.app.Activity.RESULT_OK;
import static android.content.ContentValues.TAG;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mobile_final_project.dao_transaction.ExpenseDAO;
import com.example.mobile_final_project.dao_transaction.IncomeDAO;
import com.example.mobile_final_project.model.Income;
import com.example.mobile_final_project.utils.CategoryChooser;
import com.example.mobile_final_project.utils.EditAmountTransaction;
import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


public class Add_Transaction_Income extends Fragment implements DialogCloseListener {

    private static final String newIncomeID_KEY = "newIncomeID_KEY";
    private int newIncomeID;

    DecimalFormatSymbols symbols = new DecimalFormatSymbols();


    private static DecimalFormat REAL_FORMATTER = new DecimalFormat("$ #,##0.00");

    //Componentes do layout
    private TextView incomeAmount;

    public Add_Transaction_Income() {
        // Required empty public constructor
    }

    public static Add_Transaction_Income newInstance(int newIncomeID) {
        Add_Transaction_Income fragment = new Add_Transaction_Income();
        Bundle args = new Bundle();
        args.putInt(newIncomeID_KEY, newIncomeID);
        fragment.setArguments(args);
        Log.d(TAG, "New instance log");
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            newIncomeID = getArguments().getInt(newIncomeID_KEY);
            Log.d(TAG, "new income Id:  "+newIncomeID);
        }
        Log.d(TAG, "On create add income ");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.add_transaction_income, container, false);

        build_choose_categories(v);
        build_choose_location(v);
        build_confirm_btn(v);
        Log.d(TAG, "On create view add income");
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
        Log.d(TAG, "On view created add income");
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


    EditText location_field;
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



    public void build_confirm_btn(View v){

        ImageButton confirm_btn = v.findViewById(R.id.confirm_btn);
        TextView category_label = v.findViewById(R.id.category);
        Switch isPaid_switch = v.findViewById(R.id.isPaid_switch);
        EditText description_label = v.findViewById(R.id.description);

        confirm_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Removes currency symbol from string to prevent error parsing to double
                String str_amount = incomeAmount.getText().toString().replace(getText(R.string.currency), "");

                Double amount = Double.parseDouble(str_amount.replace(",",""));
                boolean isPaid = isPaid_switch.isChecked();
                String description = description_label.getText().toString();
                String category = category_label.getText().toString();
                String location = location_field.getText().toString();

                Income newIncome = new Income(newIncomeID, description, category, location, new Date(2003, 9, 3), amount, isPaid);

                Intent returnIntent = new Intent();
                returnIntent.putExtra("newIncome", newIncome);
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
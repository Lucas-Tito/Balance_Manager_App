package com.example.mobile_final_project;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mobile_final_project.utils.EditAmountTransaction;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Add_Transaction_Income#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Add_Transaction_Income extends Fragment implements DialogCloseListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    //private static DecimalFormat REAL_FORMATTER = new DecimalFormat("0.##");

    DecimalFormatSymbols symbols = new DecimalFormatSymbols();


    private static DecimalFormat REAL_FORMATTER = new DecimalFormat("$ #,##0.00");

    //Componentes do layout
    private TextView incomeAmount;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Add_Transaction_Income() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Add_Income_Screen.
     */
    // TODO: Rename and change types and number of parameters
    public static Add_Transaction_Income newInstance(String param1, String param2) {
        Add_Transaction_Income fragment = new Add_Transaction_Income();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.add_transaction_income, container, false);
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
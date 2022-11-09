package com.example.mobile_final_project.utils;

import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.mobile_final_project.DialogCloseListener;
import com.example.mobile_final_project.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.example.mobile_final_project.dao_transaction.IncomeDAO;
import com.example.mobile_final_project.model.Income;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.text.DecimalFormat;

public class EditAmountTransaction extends BottomSheetDialogFragment
{
    private EditText edtAmountValue;
    private Button btnSaveAmount;
    private Bundle bundle;
    private static DecimalFormat REAL_FORMATTER = new DecimalFormat("$ #,##0.00");

    public static EditAmountTransaction newInstance()
    {
        return  new EditAmountTransaction();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.DialogStyle);
        bundle = this.getArguments();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_amount_transaction,container,false);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        edtAmountValue = getView().findViewById(R.id.edtAmountValue);
        System.out.println("ON VIEW CREATED");


        edtAmountValue.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    //Log.i(TAG,"Here you can write the code");
                    Double amount = Double.parseDouble(edtAmountValue.getText().toString());
                    Income.amount = amount;

                    dismiss();
                    return true;
                }
                return false;
            }
        });


    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        Activity activity = getActivity();
        TextView incomeAmount = activity.findViewById(R.id.amount);
        if(Income.amount != null)
        {
            incomeAmount.setText(REAL_FORMATTER.format(Income.amount));
        }

        System.out.println("ENTROU NO ONDISMISS");
        if(activity instanceof DialogCloseListener)
        {
            System.out.println("ENTROU IF DO DISMISS");
            ((DialogCloseListener)activity).handleDialogClose(dialog);
        }
    }
}

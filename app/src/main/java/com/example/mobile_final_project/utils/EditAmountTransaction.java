package com.example.mobile_final_project.utils;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
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
        btnSaveAmount = getView().findViewById(R.id.btnSaveAmount);
        System.out.println("ON VIEW CREATED");


        boolean isUpdate = false;
        Bundle bundle = new Bundle();
        bundle = this.getArguments();
        System.out.println("BUNDLE: "+bundle);
        String task = edtAmountValue.getText().toString();
        //newTaskText.setText(task);
        if(task.length()>0)
        {
            btnSaveAmount.setTextColor(ContextCompat.getColor(getContext(), R.color.red));
        }
        edtAmountValue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {


            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.toString().equals(""))
                {
                    System.out.println("CHAT ON CHANGE");
                    btnSaveAmount.setEnabled(false);
                    btnSaveAmount.setTextColor(Color.RED);
                }
                else
                {
                    btnSaveAmount.setEnabled(true);
                    btnSaveAmount.setTextColor(ContextCompat.getColor(getContext(), R.color.Snow));
                }
            }

            @Override
            public void afterTextChanged(Editable editable)
            {

            }


        });

        boolean finalIsUpdate = isUpdate;
        Bundle finalBundle = bundle;
        btnSaveAmount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("ENTROU NO ONCLICK DE SALVAR");
                Double amount = Double.parseDouble(edtAmountValue.getText().toString());
                Income.amount = amount;

                dismiss();
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
        System.out.println(activity);
        System.out.println(activity instanceof DialogCloseListener);
        if(activity instanceof DialogCloseListener)
        {
            System.out.println("ENTROU IF DO DISMISS");
            ((DialogCloseListener)activity).handleDialogClose(dialog);
        }
    }
}

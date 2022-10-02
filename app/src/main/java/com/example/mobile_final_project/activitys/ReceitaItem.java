package com.example.mobile_final_project.activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.mobile_final_project.R;
import com.example.mobile_final_project.model.Income;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class ReceitaItem extends AppCompatActivity {

    private TextView txtValorReceita,txtDataEntradaReceita,txtDescricaoReceita ;
    private EditText edtValorReceita,edtDataEntradaReceita,edtDescricaoReceita;

    private List<Income> listIncome = new ArrayList<Income>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receita_item);

        txtValorReceita = findViewById(R.id.txtValorReceita);
        txtDataEntradaReceita = findViewById(R.id.txtDataEntradaReceita);
        txtDescricaoReceita =  findViewById(R.id.txtDescricaoReceita);

        edtValorReceita = findViewById(R.id.edtValorReceita);
        edtDataEntradaReceita = findViewById(R.id.edtDataEntradaReceita);
        edtDescricaoReceita =  findViewById(R.id.edtDescricaoReceita);

    }

    public void adicionarReceita(View view)
    {
        double valorReceita = Double.valueOf(txtValorReceita.getText().toString());
        Date dataEntradaReceita = Date.valueOf(txtDataEntradaReceita.getText().toString());
        String descricaoReceita = txtValorReceita.getText().toString();

//        commented while activity isn't in use
//        Income income = new Income(descricaoReceita,dataEntradaReceita,valorReceita);
//
//        listIncome.add(income);

    }
}
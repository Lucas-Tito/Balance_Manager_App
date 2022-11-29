package com.example.mobile_final_project;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.mobile_final_project.dao_transaction.ExpenseDAO;
import com.example.mobile_final_project.dao_transaction.IncomeDAO;

public class Add_Transaction_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaction);

        //--------------toolbar----------------------+
        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        //-------------------------------------------+

        build_frame_layout();
        build_back_btn();

    }

    private void build_back_btn() {

        ImageButton back_btn = findViewById(R.id.back_btn);

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }


    private void build_frame_layout(){

        Fragment fragmentToStart = null;
        TextView toolbar_title = findViewById(R.id.toolbar_title);

        if(getIntent().getIntExtra("fragToStart", 0) == 1){
            int newExpenseID = getIntent().getIntExtra("newExpenseID", -1);
            fragmentToStart = Add_Transaction_Expense.newInstance(newExpenseID);
            toolbar_title.setText(R.string.toolbar_title_expense);
        }
        else if(getIntent().getIntExtra("fragToStart", 0) == 2){
            int newIncomeID = getIntent().getIntExtra("newIncomeID", -1);
            fragmentToStart = Add_Transaction_Income.newInstance(newIncomeID);
            toolbar_title.setText(R.string.toolbar_title_income);
        }

        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, fragmentToStart).commit();

    }
}
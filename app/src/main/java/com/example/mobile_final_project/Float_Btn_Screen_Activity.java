package com.example.mobile_final_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;

public class Float_Btn_Screen_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_float_btn_sceen);

        //--------------toolbar----------------------+
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("New Expense");

        setSupportActionBar(toolbar);
        //-------------------------------------------+

    }
}
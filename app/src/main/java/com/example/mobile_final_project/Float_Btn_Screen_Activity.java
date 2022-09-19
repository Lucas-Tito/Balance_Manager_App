package com.example.mobile_final_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class Float_Btn_Screen_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_float_btn_sceen);

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
            fragmentToStart = new Add_Expense_Screen();
            toolbar_title.setText("New Expense");
        }

        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, fragmentToStart).commit();

    }
}
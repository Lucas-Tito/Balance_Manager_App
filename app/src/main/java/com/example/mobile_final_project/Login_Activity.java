package com.example.mobile_final_project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class Login_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        //Inflates login fragment
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new Login()).commit();

    }

}
package com.example.mobile_final_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        build_bottom_nav();
        build_float_btns();
    }


    private boolean fab_main_clicked = false;
    public void build_float_btns(){

        FloatingActionButton fab_main, fab_expense, fab_income;
        fab_main = findViewById(R.id.float_btn_main);
        fab_expense = findViewById(R.id.float_btn_expense);
        fab_income = findViewById(R.id.float_btn_income);

        Animation rotateOpen, rotateClose, fromBottom, toBottom;
        rotateOpen = AnimationUtils.loadAnimation(this, R.anim.float_btn_rotate_open);
        rotateClose = AnimationUtils.loadAnimation(this, R.anim.float_btn_rotate_close);
        fromBottom = AnimationUtils.loadAnimation(this, R.anim.float_btn_from_bottom);
        toBottom = AnimationUtils.loadAnimation(this, R.anim.float_btn_to_bottom);



        /*handles animation and visibility of sub buttons*/
        fab_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!fab_main_clicked){
                    fab_main.startAnimation(rotateOpen);

                    fab_expense.setVisibility(View.VISIBLE);
                    fab_expense.startAnimation(fromBottom);

                    fab_income.setVisibility(View.VISIBLE);
                    fab_income.startAnimation(fromBottom);
                }
                else{
                    fab_main.startAnimation(rotateClose);

                    fab_expense.setVisibility(View.INVISIBLE);
                    fab_expense.startAnimation(toBottom);

                    fab_income.setVisibility(View.INVISIBLE);
                    fab_income.startAnimation(toBottom);
                }

                fab_main_clicked = !fab_main_clicked;
            }
        });

        fab_expense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Float_Btn_Screen_Activity.class);
                intent.putExtra("fragToStart", 1);
                startActivity(intent);
            }
        });

        fab_income.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }




    public void build_bottom_nav(){

        BottomNavigationView bottom_nav_view;

        bottom_nav_view = findViewById(R.id.bottomNavigationView);
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new Home()).commit();
        bottom_nav_view.setSelectedItemId(R.id.home);

        bottom_nav_view.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;

                switch (item.getItemId()){

                    case R.id.home:
                        fragment = new Home();
                        break;

                    case R.id.more:
                        fragment = new More_Screen();
                        break;

                }
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, fragment).commit();
                return true;
            }
        });

    }
}
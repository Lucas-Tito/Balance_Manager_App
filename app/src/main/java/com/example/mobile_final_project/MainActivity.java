package com.example.mobile_final_project;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.example.mobile_final_project.dao_transaction.ExpenseDAO;
import com.example.mobile_final_project.model.Expense;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Date;


public class MainActivity extends AppCompatActivity {

    ExpenseDAO expenseDAO = new ExpenseDAO(new Expense("teste", new Date(10, 9, 2003), 20.03));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        build_bottom_nav();
        build_float_btns();
    }


    //Override method to receive updated expenses from other activities
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101) {
            if(resultCode == MainActivity.RESULT_OK){
                expenseDAO = (ExpenseDAO) data.getSerializableExtra("newExpenseDao");
            }

        }
    }

    private boolean fab_main_clicked = false;
    private void build_float_btns(){

        FloatingActionButton fab_main, fab_expense, fab_income;
        fab_main = findViewById(R.id.float_btn_main);
        fab_expense = findViewById(R.id.float_btn_expense);
        fab_income = findViewById(R.id.float_btn_income);

        Animation rotateOpen, rotateClose, fromBottom, toBottom;
        rotateOpen = AnimationUtils.loadAnimation(this, R.anim.float_btn_rotate_open);
        rotateClose = AnimationUtils.loadAnimation(this, R.anim.float_btn_rotate_close);
        fromBottom = AnimationUtils.loadAnimation(this, R.anim.float_btn_from_bottom);
        toBottom = AnimationUtils.loadAnimation(this, R.anim.float_btn_to_bottom);

        TextView expense_label, income_label;
        expense_label = findViewById(R.id.expense_label);
        income_label = findViewById(R.id.income_label);



        /*handles animation and visibility of sub buttons*/
        fab_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!fab_main_clicked){
                    fab_main.startAnimation(rotateOpen);

                    fab_expense.setVisibility(View.VISIBLE);
                    fab_expense.startAnimation(fromBottom);
                    expense_label.setVisibility(View.VISIBLE);
                    expense_label.startAnimation(fromBottom);

                    fab_income.setVisibility(View.VISIBLE);
                    fab_income.startAnimation(fromBottom);
                    income_label.setVisibility(View.VISIBLE);
                    income_label.startAnimation(fromBottom);
                }
                else{
                    fab_main.startAnimation(rotateClose);

                    fab_expense.setVisibility(View.INVISIBLE);
                    fab_expense.startAnimation(toBottom);
                    expense_label.setVisibility(View.INVISIBLE);
                    expense_label.startAnimation(toBottom);

                    fab_income.setVisibility(View.INVISIBLE);
                    fab_income.startAnimation(toBottom);
                    income_label.setVisibility(View.INVISIBLE);
                    income_label.startAnimation(toBottom);
                }

                fab_main_clicked = !fab_main_clicked;
            }
        });

        fab_expense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Add_Transaction_Activity.class);
                intent.putExtra("fragToStart", 1);
                intent.putExtra("expenseDao", expenseDAO);
                startActivityForResult(intent, 101);
            }
        });

        fab_income.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Add_Transaction_Activity.class);
                intent.putExtra("fragToStart", 2);
                intent.putExtra("expenseDao", expenseDAO);
                startActivityForResult(intent, 101);
            }
        });

    }




    private void build_bottom_nav(){

        BottomNavigationView bottom_nav_view;

        bottom_nav_view = findViewById(R.id.bottomNavigationView);
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, Home.newInstance(Double.toString(expenseDAO.getTotal_amount()))).commit();
        bottom_nav_view.setSelectedItemId(R.id.home);

        bottom_nav_view.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;

                switch (item.getItemId()){

                    case R.id.home:
                        fragment = Home.newInstance(Double.toString(expenseDAO.getTotal_amount()));
                        break;

                    case R.id.transactions:
                        fragment = Transactions_List.newInstance(expenseDAO);
                        break;

                    case R.id.more:
                        fragment = new More();
                        break;

                }
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, fragment).commit();
                return true;
            }
        });

    }
}
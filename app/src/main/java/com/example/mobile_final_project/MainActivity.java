package com.example.mobile_final_project;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.example.mobile_final_project.dao_transaction.ExpenseDAO;
import com.example.mobile_final_project.dao_transaction.IncomeDAO;
import com.example.mobile_final_project.model.Expense;
import com.example.mobile_final_project.model.Income;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.type.DateTime;


//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;

import java.util.Date;



public class MainActivity extends AppCompatActivity {

    ExpenseDAO expenseDAO = new ExpenseDAO();//new ExpenseDAO(new Expense(0, "teste_expense", "Leisure", "place-holder-location", new Date(System.currentTimeMillis()), 20.03));
    IncomeDAO incomeDAO = new IncomeDAO();//new IncomeDAO(new Income(0, "teste_income", "Clothing", "place-holder-location", new Date(System.currentTimeMillis()), 10.03));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        incomeDAO.getFromDB(FirebaseFirestore.getInstance(),FirebaseAuth.getInstance().getCurrentUser());
        expenseDAO.getFromDB(new ExpenseDAO.FireStoreCallback() {
            @Override
            public void onCallback() {
                build_bottom_nav();
            }
        }, FirebaseFirestore.getInstance(), FirebaseAuth.getInstance().getCurrentUser());




        build_float_btns();
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        if(user == null){
            startActivity(new Intent(MainActivity.this, Login_Activity.class));
        }
    }

    //Override method to receive updated expenses from other activities
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 101)
            if(resultCode == MainActivity.RESULT_OK)
                expenseDAO.addExpense((Expense) data.getSerializableExtra("newExpense"), FirebaseFirestore.getInstance());


        if (requestCode == 102)
            if(resultCode == MainActivity.RESULT_OK)
                incomeDAO.addIncome((Income) data.getSerializableExtra(("newIncome")),FirebaseFirestore.getInstance());


        if (requestCode == 103){
            if(resultCode == MainActivity.RESULT_OK)
                expenseDAO.updateExpense((Expense) data.getSerializableExtra(("updatedExpense")), FirebaseFirestore.getInstance(), FirebaseAuth.getInstance().getCurrentUser());

            if(resultCode == 2)
                expenseDAO.removeExpense((int) data.getSerializableExtra("expenseToRemove"), FirebaseFirestore.getInstance(), FirebaseAuth.getInstance().getCurrentUser());
        }


        if (requestCode == 104){
            if(resultCode == MainActivity.RESULT_OK)
                incomeDAO.updateIncome((Income) data.getSerializableExtra(("updatedIncome")), FirebaseFirestore.getInstance(), FirebaseAuth.getInstance().getCurrentUser());

            if(resultCode == 2)
                incomeDAO.removeIncome((int)(data.getSerializableExtra("incomeToRemove")), FirebaseFirestore.getInstance(), FirebaseAuth.getInstance().getCurrentUser());
        }


        //refreshes fragment to show changes on list
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, Transactions_List.newInstance(expenseDAO, incomeDAO)).commit();
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
                    //Log.d(TAG, "Mensagem de leitura local | "+ expenseDAO.getAll().get(0)+ expenseDAO.getAll().get(1));
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
                intent.putExtra("newExpenseID", expenseDAO.getSize());
                System.out.println("SIZE: " + expenseDAO.getSize());
                startActivityForResult(intent, 101);
            }
        });

        fab_income.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Add_Transaction_Activity.class);
                intent.putExtra("fragToStart", 2);
                intent.putExtra("newIncomeID", incomeDAO.getSize());
                startActivityForResult(intent, 102);
            }
        });

    }




    private void build_bottom_nav(){

        BottomNavigationView bottom_nav_view;

        bottom_nav_view = findViewById(R.id.bottomNavigationView);
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, Home.newInstance(Double.toString(expenseDAO.getTotal_amount()), Double.toString(incomeDAO.getTotal_amount()))).commit();
        bottom_nav_view.setSelectedItemId(R.id.home);

        bottom_nav_view.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;

                switch (item.getItemId()){

                    case R.id.home:
                        fragment = Home.newInstance(Double.toString(expenseDAO.getTotal_amount()), Double.toString(incomeDAO.getTotal_amount()));
                        break;

                    case R.id.transactions:
                        fragment = Transactions_List.newInstance(expenseDAO, incomeDAO);
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
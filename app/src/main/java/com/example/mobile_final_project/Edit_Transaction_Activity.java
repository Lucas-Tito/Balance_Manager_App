package com.example.mobile_final_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.example.mobile_final_project.dao_transaction.ExpenseDAO;
import com.example.mobile_final_project.dao_transaction.IncomeDAO;

public class Edit_Transaction_Activity extends AppCompatActivity {

    private ExpenseDAO expenseDao;
    private IncomeDAO incomeDao;
    private int transaction_pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_transaction);

        //--------------toolbar----------------------+
        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        //-------------------------------------------+

        expenseDao = (ExpenseDAO) getIntent().getSerializableExtra("expenseDao");
        incomeDao = (IncomeDAO) getIntent().getSerializableExtra("incomeDao");
        transaction_pos = getIntent().getIntExtra("transaction_pos", 0);

        build_frame_layout();
        build_back_btn();
        build_delete_btn();

    }

    //---------------------------TOOLBAR------------------------------------------------------------+
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.edit_transaction_menu, menu);
        return super.onCreateOptionsMenu(menu);

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


    private void build_delete_btn(){


        Toolbar toolbar = findViewById(R.id.toolbar);

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                switch (item.getItemId()){

                    case R.id.trash:
                        if(getIntent().getStringExtra("frag_key").equals("editExpense")){
                            expenseDao.removeExpense(transaction_pos);
                            Intent returnIntent = new Intent();
                            returnIntent.putExtra("newExpenseDao", expenseDao);
                            setResult(RESULT_OK, returnIntent);
                            finish();
                        }
                        else{
                            incomeDao.removeIncome(transaction_pos);
                            Intent returnIntent = new Intent();
                            returnIntent.putExtra("newIncomeDao", incomeDao);
                            setResult(RESULT_OK, returnIntent);
                            finish();
                        }

                }

                return false;
            }
        });
    }

    private void build_frame_layout(){

        Fragment fragmentToStart = null;

        if(getIntent().getStringExtra("frag_key").equals("editExpense"))
            fragmentToStart = Edit_Transaction_Expense.newInstance(expenseDao, transaction_pos);

        else
            fragmentToStart = Edit_Transaction_Income.newInstance(incomeDao, transaction_pos);


        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, fragmentToStart).commit();


    }

}
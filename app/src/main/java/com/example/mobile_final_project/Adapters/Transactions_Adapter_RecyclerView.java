package com.example.mobile_final_project.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobile_final_project.R;
import com.example.mobile_final_project.model.Expense;
import com.example.mobile_final_project.model.Transaction;
import com.example.mobile_final_project.utils.Resource;
import com.example.mobile_final_project.utils.categoryResources;

import java.util.ArrayList;

public class Transactions_Adapter_RecyclerView extends RecyclerView.Adapter<Transactions_Adapter_RecyclerView.MyViewHolder> {

    ArrayList<Transaction> united_transactions;
    Context context;
    private final IRecyclerView_Transactions recyclerViewInterface;

    public Transactions_Adapter_RecyclerView(Context ct, ArrayList<Transaction> united_transactions, IRecyclerView_Transactions recyclerViewInterface){
        this.united_transactions = united_transactions;
        context = ct;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.bottom_nav_transactions_itens, parent, false);

        return new MyViewHolder(view);
    }

    categoryResources categoryResources = new categoryResources();
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Transaction transaction = united_transactions.get(position);
        System.out.println(position);
        Resource resource = categoryResources.getValues(transaction.getCategory());

        holder.date_label.setText(transaction.getDate().toString());
        holder.description_label.setText(transaction.getDescription());
        holder.category_label.setText(transaction.getCategory());
        holder.amount_label.setText(context.getString(R.string.currency) + transaction.getValue());
        holder.category_ic.setImageResource(resource.getImg());
        holder.category_ic.setBackgroundTintList(context.getResources().getColorStateList(resource.getColor()));


        if(!transaction.getIsPaid())
            holder.isReceived_label.setVisibility(View.INVISIBLE);

        if(transaction instanceof Expense)
            holder.amount_label.setTextColor(context.getResources().getColor(R.color.light_red));

        else
            holder.amount_label.setTextColor(context.getResources().getColor(R.color.light_green));

    }

    @Override
    public int getItemCount() {
        return united_transactions.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView date_label, description_label,
                category_label, amount_label;
        ImageView category_ic, isReceived_label;

        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            date_label = itemView.findViewById(R.id.date_label);
            description_label = itemView.findViewById(R.id.description_label);
            category_label = itemView.findViewById(R.id.category_label);
            amount_label = itemView.findViewById(R.id.amount_label);
            category_ic = itemView.findViewById(R.id.category_ic);
            isReceived_label = itemView.findViewById(R.id.isReceived_label);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(recyclerViewInterface!=null){
                        int pos = getAdapterPosition();
                        Transaction transaction = united_transactions.get(pos);
                        pos = transaction.getId();
                        String fragToStart;

                        if(transaction instanceof Expense)
                            fragToStart = "editExpense";
                        else
                            fragToStart = "editIncome";

                        recyclerViewInterface.onItemClick(pos, fragToStart);
                    }
                }
            });
        }
    }

}

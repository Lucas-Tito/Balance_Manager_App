package com.example.mobile_final_project.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobile_final_project.R;
import com.example.mobile_final_project.model.Despesa;

import java.util.ArrayList;

public class Adapter_RecyclerView extends RecyclerView.Adapter<Adapter_RecyclerView.MyViewHolder> {

    ArrayList<Despesa> expenses = new ArrayList<>();
    Context context;
    private final Interface_RecyclerView recyclerViewInterface;

    public Adapter_RecyclerView(Context ct, ArrayList<Despesa> expenses, Interface_RecyclerView recyclerViewInterface){
        this.expenses = expenses;
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

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.date_label.setText(expenses.get(position).getData().toString());
        holder.description_label.setText(expenses.get(position).getDescricao());
        holder.category_label.setText(expenses.get(position).getCategoria());
        holder.amount_label.setText(String.valueOf(expenses.get(position).getValor()));

    }

    @Override
    public int getItemCount() {
        return expenses.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView date_label, description_label,
                category_label, amount_label;

        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            date_label = itemView.findViewById(R.id.date_label);
            description_label = itemView.findViewById(R.id.description_label);
            category_label = itemView.findViewById(R.id.category_label);
            amount_label = itemView.findViewById(R.id.amount_label);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(recyclerViewInterface!=null){
                        int pos = getAdapterPosition();

                        //if position is valid
                        if(pos!=RecyclerView.NO_POSITION){
                            recyclerViewInterface.onItemClick(pos);
                        }
                    }
                }
            });
        }
    }

}

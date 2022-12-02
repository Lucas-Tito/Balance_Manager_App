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

import java.util.ArrayList;

public class Categories_Adapter_RecyclerView extends RecyclerView.Adapter<Categories_Adapter_RecyclerView.MyViewHolder> {


    String category_names[] = {"Clothing", "Education", "Electronics", "Health", "Leisure", "Food", "SuperMarket", "Others"};
    int category_img[] = {R.drawable.categories_clothing, R.drawable.categories_education, R.drawable.categories_education, R.drawable.categories_eletronics,
    R.drawable.categories_health, R.drawable.categories_leisure, R.drawable.categories_food, R.drawable.categories_supermarket, R.drawable.categories_others};

    Context context;
    private final Interface_RecyclerView recyclerViewInterface;

    public Categories_Adapter_RecyclerView(Context ct, Interface_RecyclerView recyclerViewInterface){
/*      this.category_names = category_names;
        this.category_img = category_img;*/
        //for now i will only use pre-set data

        this.context = ct;
        this.recyclerViewInterface = recyclerViewInterface;
    }


    @NonNull
    @Override
    public Categories_Adapter_RecyclerView.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.transaction_category_options_itens, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Categories_Adapter_RecyclerView.MyViewHolder holder, int position) {
        holder.category_label.setText(category_names[position]);
        holder.category_img.setImageResource(category_img[position]);
    }

    @Override
    public int getItemCount() {
        return category_names.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView category_img;
        TextView category_label;

        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            category_img = itemView.findViewById(R.id.category_img);
            category_label = itemView.findViewById(R.id.category_name);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(recyclerViewInterface!=null){


                    }
                }
            });
        }
    }
}

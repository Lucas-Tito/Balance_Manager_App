package com.example.mobile_final_project.utils;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobile_final_project.Adapters.Categories_Adapter_RecyclerView;
import com.example.mobile_final_project.Adapters.IRecyclerView_Categories;
import com.example.mobile_final_project.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class CategoryChooser extends BottomSheetDialogFragment implements IRecyclerView_Categories{

    public CategoryChooser() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.transaction_category_options, container, false);



        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        getDialog().getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        getDialog().getWindow().setGravity(Gravity.BOTTOM);

        RecyclerView category_options = v.findViewById(R.id.recyclerView);
        Categories_Adapter_RecyclerView adapter = new Categories_Adapter_RecyclerView(getContext(), this);
        category_options.setAdapter(adapter);
        category_options.setLayoutManager(new LinearLayoutManager(getActivity()));

        return v;
    }

    @Override
    public void onItemClick(String category_name) {
        TextView category = getActivity().findViewById(R.id.category);
        category.setText(category_name);
        dismiss();
    }
}

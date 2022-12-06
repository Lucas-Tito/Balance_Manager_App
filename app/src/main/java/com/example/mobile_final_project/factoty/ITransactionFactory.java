package com.example.mobile_final_project.factoty;

import com.example.mobile_final_project.model.Expense;
import com.example.mobile_final_project.model.Income;
import com.example.mobile_final_project.viewmodel.TransactionDBViewModel;

import java.util.Map;

public interface ITransactionFactory {
    Map<String, Object> daoToTransactionDB(Object transaction);
}

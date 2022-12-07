package com.example.mobile_final_project.factory;

import com.example.mobile_final_project.model.Transaction;
import com.example.mobile_final_project.viewmodel.TransactionDBViewModel;

import java.util.Map;

public interface ITransactionFactory {
    Map<String, Object> daoToTransactionDB(Object transaction);
    Transaction transactionDBToDao(TransactionDBViewModel transaction);
}

package com.example.mobile_final_project.factoty;

import com.example.mobile_final_project.model.Transaction;
import com.example.mobile_final_project.viewmodel.TransactionDBViewModel;

import java.io.Serializable;
import java.util.Map;

public interface ITransactionFactory extends Serializable {
    Map<String, Object> daoToTransactionDB(Object transaction);
    Transaction transactionDBToDao(TransactionDBViewModel transaction);
}

package com.paymybudy.payapp.repository;

import com.paymybudy.payapp.model.BankTransaction;
import org.springframework.data.repository.CrudRepository;

public interface BankTransactionRepository extends CrudRepository < BankTransaction, Integer> {
    /**
     * here are all the methods used to access and manipulate datas in BankTransaction table
     */


}

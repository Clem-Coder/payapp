package com.paymybudy.payapp.repository;

import com.paymybudy.payapp.model.BankAccount;
import com.paymybudy.payapp.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface BankAccountRepository extends CrudRepository<BankAccount, String> {

    /**
     * here are all the methods used to access and manipulate datas in BankAccount table
     */

    List<BankAccount> getBankAccountByUser(User user);

    BankAccount getBankAccountByUserAndAccountType(User user, String accountType);

    List<BankAccount> findByAccountId(String accountNumber);
}

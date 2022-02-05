package com.paymybudy.payapp.service;

import com.paymybudy.payapp.model.BankAccount;
import com.paymybudy.payapp.model.User;
import com.paymybudy.payapp.repository.BankAccountRepository;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * here are all the methods used to manipulate (Create,read, update & delete) data from BankAccount table
 */

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BankAccountService {

    private static final Logger logger = LogManager.getLogger("BankAccountService");

    private final BankAccountRepository bankAccountRepository;


    /**
     * Get all the user bank account
     */
    @Transactional(readOnly = true)
    public List<BankAccount> getUserBankAccounts(User user){
        logger.info("New request: get bank accounts of the user: " + user.getEmail());
        return bankAccountRepository.getBankAccountByUser(user);
    }

    /**
     * get a bank account of the user
     */
    @Transactional(readOnly = true)
    public BankAccount getUserBankAccount(User user, String accountType){
        logger.info("New request: get the bank account:" + accountType +" of the user: " + user.getEmail());
        return bankAccountRepository.getBankAccountByUserAndAccountType(user, accountType);
    }

    /**
     * Tranfer money from the app to a bank account
     */
    @Transactional
    public void bankTransfer(BankAccount bankAccount, double amount){
        logger.info("New bank transfer: to the bank account: " + bankAccount.getAccountType() + " of the user: " + bankAccount.getUser().getEmail() + ", amount:" + amount);
        double balance = bankAccount.getBankBalance() + amount;
        bankAccount.setBankBalance(balance);
        bankAccountRepository.save(bankAccount);
    }

    /**
     * Tranfer money from a bank account to the app
     */
    @Transactional
    public void accountTransfer(BankAccount bankAccount, double amount){
        logger.info("New account transfer: from the bank account: " + bankAccount.getAccountType() + " of the user: " + bankAccount.getUser().getEmail() + ", amount:" + amount);
        double balance = bankAccount.getBankBalance() - amount;
        bankAccount.setBankBalance(balance);
        bankAccountRepository.save(bankAccount);
    }

    /**
     * Save a bank account in database
     */
    @Transactional
    public void saveBankAccount(BankAccount bankAccount){
        logger.info("New request: save the bank account " + bankAccount.getAccountId() + " in database");
        bankAccountRepository.save(bankAccount);
    }

    /**
     * find a bank account in db by account number
     */
    @Transactional(readOnly = true)
    public List<BankAccount> getBankAccountByAccountNumber(String accountNumber){
        logger.info("New request: get bank account by account number: " + accountNumber);
        return bankAccountRepository.findByAccountId(accountNumber);
    }

}

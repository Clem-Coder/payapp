package com.paymybudy.payapp.service;

import com.paymybudy.payapp.model.BankAccount;
import com.paymybudy.payapp.model.User;
import com.paymybudy.payapp.repository.BankAccountRepository;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BankAccountService {

    /**
     * here are all the methods use to manipulate (Create,read, update & delete) datas from BankAccount table
     */
    private static final Logger logger = LogManager.getLogger("BankAccountService");

    private final BankAccountRepository bankAccountRepository;

    public List<BankAccount> getUserBankAccounts(User user){
        logger.info("New request: get bank accounts of the user: " + user.getEmail());
        return bankAccountRepository.getBankAccountByUser(user);
    }

    public BankAccount getUserBankAccount(User user, String accountType){
        logger.info("New request: get the bank account:" + accountType +" of the user: " + user.getEmail());
        return bankAccountRepository.getBankAccountByUserAndAccountType(user, accountType);
    }

    public void bankTransfer(BankAccount bankAccount, double amount){
        logger.info("New bank transfer: to the bank account: " + bankAccount.getAccountType() + " of the user: " + bankAccount.getUser().getEmail() + ", amount:" + amount);
        double balance = bankAccount.getBankBalance() + amount;
        bankAccount.setBankBalance(balance);
        bankAccountRepository.save(bankAccount);
    }

    public void accountTransfer(BankAccount bankAccount, double amount){
        logger.info("New account transfer: from the bank account: " + bankAccount.getAccountType() + " of the user: " + bankAccount.getUser().getEmail() + ", amount:" + amount);
        double balance = bankAccount.getBankBalance() - amount;
        bankAccount.setBankBalance(balance);
        bankAccountRepository.save(bankAccount);
    }

    public void saveBankAccount(BankAccount bankAccount){
        logger.info("New request: save the bank account " + bankAccount.getAccountId() + " in database");
        bankAccountRepository.save(bankAccount);
    }

    public List<BankAccount> getBankAccountByAccountNumber(String accountNumber){
        logger.info("New request: get bank account by account number: " + accountNumber);
        return bankAccountRepository.findByAccountId(accountNumber);
    }

}

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
     *
     * @param user the logged user
     * @return a list of all the user bank accounts
     */

    @Transactional(readOnly = true)
    public List<BankAccount> getUserBankAccounts(User user){
        logger.info("New request: get bank accounts of the user: " + user.getEmail());
        return bankAccountRepository.getBankAccountByUser(user);
    }

    /**
     * get a bank account of the user
     *
     * @param user the logged user
     * @param accountType the type of the bank account (Savings account, Fixed deposit account, NRI accounts,Recurring deposit account)
     *
     * @return a bank account with the type in parameter
     */
    @Transactional(readOnly = true)
    public BankAccount getUserBankAccount(User user, String accountType){
        logger.info("New request: get the bank account:" + accountType +" of the user: " + user.getEmail());
        return bankAccountRepository.getBankAccountByUserAndAccountType(user, accountType);
    }

    /**
     * Tranfer money from the app to a bank account
     *
     * @param bankAccount the bank account of the logged user where the money will be added
     * @param amount the amount of the transfer
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
     *
     *  @param bankAccount the bank account of the logged user where the money will be subtracted
     *  @param amount the amount of the transfer
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
     *
     * @param bankAccount the bank account who will be save in database
     */
    @Transactional
    public void saveBankAccount(BankAccount bankAccount){
        logger.info("New request: save the bank account " + bankAccount.getAccountId() + " in database");
        bankAccountRepository.save(bankAccount);
    }

    /**
     * find a bank account in db by account number
     *
     * @param accountNumber the account id search in database
     * @return a list of bank account find in database
     */
    @Transactional(readOnly = true)
    public List<BankAccount> getBankAccountByAccountNumber(String accountNumber){
        logger.info("New request: get bank account by account number: " + accountNumber);
        return bankAccountRepository.findByAccountId(accountNumber);
    }

}

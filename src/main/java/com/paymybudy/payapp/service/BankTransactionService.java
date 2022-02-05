package com.paymybudy.payapp.service;

import com.paymybudy.payapp.model.BankTransaction;
import com.paymybudy.payapp.model.User;
import com.paymybudy.payapp.repository.BankTransactionRepository;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * here are all the methods use to manipulate (Create,read, update & delete) datas from BankTransaction table
 */

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BankTransactionService {

    private static final Logger logger = LogManager.getLogger("BankTransactionService");

    private final BankTransactionRepository bankTransactionRepository;

    /**
     * Save a new bank transaction in database
     *
     * @param bankTransaction the bankTransaction to save in database
     */
    @Transactional
    public void saveBankTransaction(BankTransaction bankTransaction){
        logger.info("Save the bank transaction: " + bankTransaction.getTransactionId() + "in database ");
        bankTransactionRepository.save(bankTransaction);
    }


    /**
     *This method will create and return a new bank transaction
     *it takes three parameters : An amount, a transfer type and an account id.
     *
     * @param user the logged user
     * @param amount the amount of the new bank transaction
     * @param transferType the type of transfer (from pay my buddy to bank, or from bank to pay my buddy)
     * @param accountId the id of the bank account
     * @return a new bankTransaction
     */
    public BankTransaction newBankTransaction (User user, double amount, String transferType, String accountId) {
        logger.info("Create new bank transaction: Debtor: " + user.getEmail() + ", amount: " + amount + ", transfer type: " + transferType + ", account id: " + accountId);
        BankTransaction bankTransaction = new BankTransaction();
        Date timeOfTransaction = new Date();
        bankTransaction.setTimeOfTransaction(timeOfTransaction);
        bankTransaction.setDebtor(user);
        bankTransaction.setAmount(amount);
        bankTransaction.setAccountId(accountId);
        bankTransaction.setTransferType(transferType);
        return bankTransaction;
    }
}

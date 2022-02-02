package com.paymybudy.payapp.service;

import com.paymybudy.payapp.model.BankTransaction;
import com.paymybudy.payapp.model.User;
import com.paymybudy.payapp.repository.BankTransactionRepository;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BankTransactionService {

    /**
     * here are all the methods use to manipulate (Create,read, update & delete) datas from BankTransaction table
     */

    private static final Logger logger = LogManager.getLogger("BankTransactionService");

    private final BankTransactionRepository bankTransactionRepository;

    public void saveBankTransaction(BankTransaction bankTransaction){
        logger.info("Save the bank transaction: " + bankTransaction.getTransactionId() + "in database ");
        bankTransactionRepository.save(bankTransaction);
    }

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

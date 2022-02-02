package com.paymybudy.payapp.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="user_bank_transaction")
public class BankTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int transactionId;
    private double amount;
    private Date timeOfTransaction;
    private String transferType;
    private String accountId;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User debtor;




    //GETTERS AND SETTERS

    public int getTransactionId() {
        return transactionId;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setTimeOfTransaction(Date timeOfTransaction) {
        this.timeOfTransaction = timeOfTransaction;
    }

    public void setTransferType(String transferType) {
        this.transferType = transferType;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }
    public void setDebtor(User debtor) {
        this.debtor = debtor;
    }
}

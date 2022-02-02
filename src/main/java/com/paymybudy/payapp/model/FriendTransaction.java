package com.paymybudy.payapp.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="user_friend_transaction")
public class FriendTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int transactionId;
    private double amount;
    private Date timeOfTransaction;
    private String comment;
    private int creditorId;
    private String userName;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User debtor;

    @Column(name = "creditor_name")
    private String creditorName;


    //GETTERS AND SETTERS

    public int getTransactionId() {
        return transactionId;
    }


    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }


    public void setTimeOfTransaction(Date timeOfTransaction) {
        this.timeOfTransaction = timeOfTransaction;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }


    public void setCreditorId(int creditorId) {
        this.creditorId = creditorId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


    public void setDebtor(User debtor) {
        this.debtor = debtor;
    }

    public String getCreditorName() {
        return creditorName;
    }

    public void setCreditorName(String creditorName) {
        this.creditorName = creditorName;
    }
}

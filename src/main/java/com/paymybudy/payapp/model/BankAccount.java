package com.paymybudy.payapp.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="bank_account")
public class BankAccount {

    @Id
    private String accountId;
    private String accountType;
    private double bankBalance;

    @ManyToOne
    @JoinColumn (name = "userId")
    private User user;



    //GETTERS AND SETTERS

    public String getAccountId() {
        return accountId;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public double getBankBalance() {
        return bankBalance;
    }

    public void setBankBalance(double bankBalance) {
        this.bankBalance = bankBalance;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

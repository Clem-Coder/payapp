package com.paymybudy.payapp.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private double balance;
    private String role;
    private int enabled;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    List<Friend> friendsList = new ArrayList<>();

    @OneToMany( mappedBy = "user")
    List<BankAccount> bankAccounts = new ArrayList<>();

    @OneToMany(mappedBy = "debtor")
    List<FriendTransaction> FriendTransactions = new ArrayList<>();

    @OneToMany(mappedBy = "debtor")
    List<BankTransaction> BankTransactions = new ArrayList<>();



    //GETTERS AND SETTERS

    public int getUserId() {
        return userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public List<Friend> getFriendsList() {
        return friendsList;
    }

    public List<BankAccount> getBankAccounts() {
        return bankAccounts;
    }

}

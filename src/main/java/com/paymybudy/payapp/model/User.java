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


    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE, orphanRemoval = true )
    List<Friend> friendsList = new ArrayList<>();

    @OneToMany( mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
    List<BankAccount> bankAccounts = new ArrayList<>();

    @OneToMany(mappedBy = "debtor", cascade = CascadeType.REMOVE, orphanRemoval = true)
    List<FriendTransaction> FriendTransactions = new ArrayList<>();

    @OneToMany(mappedBy = "debtor", cascade = CascadeType.REMOVE, orphanRemoval = true)
    List<BankTransaction> BankTransactions = new ArrayList<>();



    //GETTERS AND SETTERS


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getEnabled() {
        return enabled;
    }

    public void setEnabled(int enabled) {
        this.enabled = enabled;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public List<Friend> getFriendsList() {
        return friendsList;
    }

    public void setFriendsList(List<Friend> friendsList) {
        this.friendsList = friendsList;
    }
}

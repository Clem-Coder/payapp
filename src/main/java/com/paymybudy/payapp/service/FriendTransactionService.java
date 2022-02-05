package com.paymybudy.payapp.service;

import com.paymybudy.payapp.model.FriendTransaction;
import com.paymybudy.payapp.model.User;
import com.paymybudy.payapp.repository.FriendTransactionRepository;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * here are all the methods use to manipulate (Create,read, update & delete) datas from FriendTransaction table
 */

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class FriendTransactionService {


    private static final Logger logger = LogManager.getLogger("FriendTransactionService");

    private final PayMyBuddyService payMyBuddyService;

    private final UserService userService;

    private final FriendTransactionRepository friendTransactionRepository;


    /**
     * This method will return a page of friend transactions
     */
    @Transactional(readOnly = true)
    public Page<FriendTransaction> getFriendTransactionsPage(User user, int creditorId, Pageable pageable){
        logger.info("New request: get the friend transaction page for the user: " + user.getEmail());
        return friendTransactionRepository.getFriendTransactionByDebtorOrCreditorId(user, creditorId, pageable);
    }

    /**
     * This method save a new friend transaction in database
     */
    @Transactional
    public void saveFriendTransactions(FriendTransaction friendTransaction){
        logger.info("New request: save the friend transaction: " + friendTransaction.getTransactionId() + " in database");
        friendTransactionRepository.save(friendTransaction);
    }

    /**
     * This method will give the number of friend transaction page
     */
    public List<Integer> getNumberOfPages(Page<FriendTransaction> friendTransactionPage){
        logger.info("New request: get the number of page for the friend transaction historical ");
        List<Integer> numberOfPages = new ArrayList<>();
        for(int i = 0; i != friendTransactionPage.getTotalPages(); i++ ){
            numberOfPages.add(i);
        }return numberOfPages;
    }

    /**
     * This method will create and return a new friend transaction
     */
    @Transactional
    public FriendTransaction newFriendTransaction(User user, String friendEmail, double amount, String comment) {
        logger.info("Create new friend transaction, from: " + user.getEmail() + ", to: " + friendEmail + ", amount: " + amount + ", comment: " + comment);
        FriendTransaction friendTransaction = new FriendTransaction();
        double friendAmount =  payMyBuddyService.takeTransactionCommission(amount);
        try {
            List<User> friendList = userService.findByEmail(friendEmail);
            User friend = friendList.get(0);
            userService.subtractMoney(user, amount);
            userService.addMoney(friend, friendAmount);
            Date timeOfTransaction = new Date();
            friendTransaction.setTimeOfTransaction(timeOfTransaction);
            friendTransaction.setAmount(friendAmount);
            friendTransaction.setComment(comment);
            friendTransaction.setDebtor(user);
            friendTransaction.setUserName(user.getFirstName());
            friendTransaction.setCreditorId(friend.getUserId());
            friendTransaction.setCreditorName(friend.getFirstName());
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        return friendTransaction;
        }
    }


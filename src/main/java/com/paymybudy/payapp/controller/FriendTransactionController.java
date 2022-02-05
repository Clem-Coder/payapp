package com.paymybudy.payapp.controller;

import com.paymybudy.payapp.model.Friend;
import com.paymybudy.payapp.model.FriendTransaction;
import com.paymybudy.payapp.model.User;
import com.paymybudy.payapp.service.FriendService;
import com.paymybudy.payapp.service.FriendTransactionService;
import com.paymybudy.payapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.transaction.Transactional;
import java.util.List;

/**
 * here are all the methods to show the friend transfer page, to transfer money to a friend and to use the pagination system in the friend transfer template
 */

@SessionAttributes("user")
@Controller
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class FriendTransactionController {

    private static final Logger logger = LogManager.getLogger("FriendTransactionController");

    private final FriendService friendService;

    private final FriendTransactionService friendTransactionService;


    /**
     * Show the template to transfer money to a friend
     *
     * @param user the logged user
     * @return the friend transfer template
     */
    @GetMapping("/transferForm")
    public ModelAndView transferForm(@ModelAttribute("user") User user) {
        logger.info("New request: show the transfer-form template in the view ");
        ModelAndView mav = new ModelAndView("transfer-form");
        List<Friend> friendsList = user.getFriendsList();
        mav.addObject("friends", friendsList);
        Page<FriendTransaction> friendTransactionPage = friendTransactionService.getFriendTransactionsPage(user, user.getUserId(), PageRequest.of(0, 3, Sort.by("timeOfTransaction").descending()));
        mav.addObject("friendTransactionPage", friendTransactionPage);
        if (friendTransactionPage.getTotalPages() >= 2) {
            List<Integer> numberOfPage = friendTransactionService.getNumberOfPages(friendTransactionPage);
            mav.addObject("numberOfPage", numberOfPage);
        }   return mav;
    }

    /**
     * This method use the pagination system of the friend transfer template
     *
     * @param user the logged user
     * @param pageNumber the page to show in the transaction history
     * @return the friend transfer template
     */
    @GetMapping("/transferPage")
    public ModelAndView transferForm(@ModelAttribute("user") User user, @RequestParam("pageNumber") int pageNumber) {
        logger.info("New request: use the pagination system of the transfer-form template, show the page number: " + pageNumber);
        ModelAndView mav = new ModelAndView("transfer-form");
        List<Friend> friendsList = friendService.getUserFriendsList(user);
        mav.addObject("friends", friendsList);
        Page<FriendTransaction> friendTransactionPage = friendTransactionService.getFriendTransactionsPage(user, user.getUserId(), PageRequest.of(pageNumber, 3, Sort.by("timeOfTransaction").descending()));
        mav.addObject("friendTransactionPage", friendTransactionPage);
        if (friendTransactionPage.getTotalPages() >= 2) {
            List<Integer> numberOfPage = friendTransactionService.getNumberOfPages(friendTransactionPage);
            mav.addObject("numberOfPage", numberOfPage);
        }   return mav;
    }


    /**
     *This method allow user to transfer money to or from there bank accounts
     *it takes three parameters : An amount, a friend email and a comment.
     * If the comment is missing, the method will work, but if one of the others parameters
     * is missing, the method failed.
     *
     * @param user the logged user
     * @param friendEmail the user email who will receive the money
     * @param amount the amount of the transaction
     * @param comment a comment to describe the transaction
     * @return the friend transfer template
     */
    @PostMapping("/transfer")
    public ModelAndView transfer(@ModelAttribute("user") User user, @RequestParam String friendEmail, @RequestParam  double amount, @RequestParam String comment) {
        logger.info("New transfer from: " + user.getEmail() + ", to: " + friendEmail);
        ModelAndView mav = new ModelAndView("transfer-form");
        List<Friend> friendsList = friendService.getUserFriendsList(user);
        mav.addObject("friends", friendsList);
        Page<FriendTransaction> friendTransactionPage = friendTransactionService.getFriendTransactionsPage(user, user.getUserId(), PageRequest.of(0, 3, Sort.by("timeOfTransaction").descending()));
        mav.addObject("friendTransactionPage", friendTransactionPage);
        if (friendTransactionPage.getTotalPages() >= 2) {
            List<Integer> numberOfPage = friendTransactionService.getNumberOfPages(friendTransactionPage);
            mav.addObject("numberOfPage", numberOfPage);
        }
        if(friendEmail.equalsIgnoreCase("select a connection")){
            logger.info("Error: the user didnt select a connection");
            mav.addObject("error", "Please choose a buddy");
            return mav;
        }
        if (amount <= 0 )  {
            logger.info("Error: the user didnt enter a positive value");
            mav.addObject("error", "Please enter a positive value");
            return mav;
        }
        if (user.getBalance() < amount){
            logger.info("Error: the user dont have enough money");
            mav.addObject("error", "you do not have enough money");
            return mav;
        }
        if (user.getBalance() >= amount && amount > 0) {
            logger.info("Transaction completed correctly, amount: " + amount);
            FriendTransaction friendTransaction = friendTransactionService.newFriendTransaction(user, friendEmail, amount, comment);
            friendTransactionService.saveFriendTransactions(friendTransaction);
            friendTransactionPage = friendTransactionService.getFriendTransactionsPage(user, user.getUserId(), PageRequest.of(0, 3, Sort.by("timeOfTransaction").descending()));
            mav.addObject("friendTransactionPage", friendTransactionPage);
            mav.addObject("success", "successful transfer");
            }
        return mav;
        }
    }


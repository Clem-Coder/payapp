package com.paymybudy.payapp.service;

import com.paymybudy.payapp.model.BankAccount;
import com.paymybudy.payapp.model.Friend;
import com.paymybudy.payapp.model.User;
import com.paymybudy.payapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;


/**
 * here are all the methods used to manipulate (Create,read, update & delete) datas from User table
 */

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserService {

    private static final Logger logger = LogManager.getLogger("UserService");

    private final UserRepository userRepository;


    /**
     * This method save a user in database
     */
    @Transactional
    public void saveUser(User user){
        userRepository.save(user);
    }


    /**
     * This method return a list of all users in database
     */
    @Transactional(readOnly = true)
    public List<User> getUsers(){
        return userRepository.findAll();
    }


    /**
     * This method find and return a user in database by using an email address
     */
    @Transactional(readOnly = true)
    public List<User> findByEmail(String email){
        logger.info("New request:  find user by email: " + email);
        return userRepository.findByEmail(email);
    }

    /**
     * This method will subtract money from the user balance
     */
    @Transactional
    public void subtractMoney (User user, double amount){
        logger.info("New request:  subtract money, amount : " + amount + ", from the balance of the user: " + user.getEmail());
        double balance = user.getBalance();
        double userBalance = Math.round((balance - amount)*100)/100.0;;
        user.setBalance( userBalance);
        userRepository.save(user);
    }

    /**
     * This method will add money in the user balance
     */
    @Transactional
    public void addMoney (User user, double amount){
        logger.info("New request:  add money, amount : " + amount + ", to the balance of the user: " + user.getEmail());
        double balance = user.getBalance();
        double userBalance = Math.round((balance + amount)*100)/100.0;;
        user.setBalance( userBalance);
        userRepository.save(user);
    }


    /**
     * This method return the email address of the logged user, and find this user in db
     */
    @Transactional(readOnly = true)
    public User getLoggedUser(HttpServletRequest httpServletRequest){
        logger.info("New request:  get the email of logged user with sprig security ");
        HttpSession httpSession = httpServletRequest.getSession();
        SecurityContext securityContext = (SecurityContext) httpSession.getAttribute("SPRING_SECURITY_CONTEXT");
        String loggedUserEmail = securityContext.getAuthentication().getName();
        List<User> users = userRepository.findByEmail(loggedUserEmail);
        User user = users.get(0);
        return user;
    }

    /**
     * This method return the email of all users in database
     */
    @Transactional(readOnly = true)
    public List<String> getUsersEmail(){
        logger.info("New request:  get emails of all users in database");
        List<User> users = userRepository.findAll();
        List<String> emails = new ArrayList<>();
        for (User user: users){
            emails.add(user.getEmail());
        }
        return emails;
    }

}

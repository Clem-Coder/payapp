package com.paymybudy.payapp.controller;

import com.paymybudy.payapp.model.BankAccount;
import com.paymybudy.payapp.model.User;
import com.paymybudy.payapp.service.BankAccountService;
import com.paymybudy.payapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@SessionAttributes("user")
@Controller
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BankAccountController {

    /**
     * here are all the methods to show the template to add a new bank account and to add a new bank account
     */

    private final BankAccountService bankAccountService;

    private static final Logger logger = LogManager.getLogger("BankAccountController");

    @GetMapping("/addBankAccountForm")
    public ModelAndView addBankAccountForm(@ModelAttribute("user") User user) {
        logger.info("New request: show the add-band-account template in the view ");
        ModelAndView mav = new ModelAndView("add-bank-account-form");
        mav.addObject("check", "check");
        return mav;
    }

    @PostMapping ("/addBankAccount")
    public ModelAndView addBankAccount(@ModelAttribute("user") User user, @RequestParam String accountNumber, @RequestParam String accountType) {
        logger.info("New request: add a new bank account: " + accountNumber + "to the user: " + user.getEmail());
        ModelAndView mav = new ModelAndView("add-bank-account-form");
        List<BankAccount> bankAccount = bankAccountService.getBankAccountByAccountNumber(accountNumber);
        if(accountNumber.isEmpty()){
            mav.addObject("userError", "Please enter an account number ");
            return mav;
        }
        else if(accountType.isEmpty()){
            mav.addObject("accountTypeError", "Please select an account type ");
            return mav;
        }
        else if(bankAccount.isEmpty()){
            mav.addObject("userError", "Please enter a valid account number ");
            return mav;
        }
        else if(!bankAccount.isEmpty() && !accountType.isEmpty()){
            BankAccount ba = bankAccount.get(0);
            if(ba.getUser() != null){
                mav.addObject("userError", "This bank account is already join to a user! ");
                return mav;
            }
            else {
                List<BankAccount> bankAccounts = bankAccountService.getUserBankAccounts(user);
                for (BankAccount account : bankAccounts){
                    if (account.getAccountType().equalsIgnoreCase(accountType)){
                        mav.addObject("accountTypeError", "You already have this kind of bank account ");
                        return mav;}
                }
                ba.setAccountType(accountType);
                ba.setUser(user);
                bankAccountService.saveBankAccount(ba);
                mav.addObject("success", "bank account successfully added !");
                mav.addObject("check", "check");
            }
        }
        return mav;
    }
}

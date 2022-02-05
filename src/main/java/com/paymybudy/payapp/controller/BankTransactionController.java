package com.paymybudy.payapp.controller;

import com.paymybudy.payapp.model.BankAccount;
import com.paymybudy.payapp.model.BankTransaction;
import com.paymybudy.payapp.model.User;
import com.paymybudy.payapp.service.BankAccountService;
import com.paymybudy.payapp.service.BankTransactionService;
import com.paymybudy.payapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.transaction.Transactional;
import java.util.List;

/**
 * here are all the methods to  show the bank transfer page and to do a new bank transfer
 */

@SessionAttributes("user")
@Controller
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BankTransactionController {

    private static final Logger logger = LogManager.getLogger("BankTransactionController");

    private final BankAccountService bankAccountService;

    private final UserService userService;

    private final BankTransactionService bankTransactionService;

    /**
     * Show the template to transfer money to a bank account
     *
     * @param user the logged user
     * @return the bank transfer template
     */
    @GetMapping("/bankTransferForm")
    public ModelAndView bankTransferForm(@ModelAttribute("user") User user) {
        logger.info("New request: show the bank-transfer-form template in the view ");
        ModelAndView mav = new ModelAndView("bank-transfer-form");
        List<BankAccount> bankAccounts = bankAccountService.getUserBankAccounts(user);
        mav.addObject("bankAccounts", bankAccounts);
        return mav;
    }

    /**
     *
     *This method allow user to transfer money to or from there bank accounts
     *it takes three parameters : An amount, an account type and a transfer type.
     * If one of them is missing, the method failed.
     *
     *
     * @param user the logged user
     * @param amount the amount of the transfer
     * @param accountType the type of the bank account (Savings account, Fixed deposit account, NRI accounts,Recurring deposit account)
     * @param transferType the type of transfer (from pay my buddy to bank, or from bank to pay my buddy)
     * @return the bank transfer template
     */
    @PostMapping("/bankTransfer")
    @Transactional
    public ModelAndView bankTransfer(@ModelAttribute("user") User user, @RequestParam double amount, @RequestParam String accountType, @RequestParam String transferType) {
        logger.info("New bank transfer");
        ModelAndView mav = new ModelAndView("bank-transfer-form");
        BankAccount bankAccount = bankAccountService.getUserBankAccount(user, accountType);
        List<BankAccount> bankAccounts = bankAccountService.getUserBankAccounts(user);
        mav.addObject("bankAccounts", bankAccounts);
        if (amount <= 0)  {
            logger.info("Error: the user didnt enter a positive value ");
            mav.addObject("error", "Please enter a positive value");
            return mav;
        }
        if(accountType.equalsIgnoreCase("select bank account")){
            logger.info("Error: the user didnt select an account type ");
            mav.addObject("bankError", "Please choose account type");
            return mav;
        }
        if(transferType.contains("bank") && user.getBalance() >= amount ){
            logger.info("From the bank account : " + accountType + ", of the user: " + user.getEmail() + ", amount: " + amount);
            BankTransaction bankTransaction = bankTransactionService.newBankTransaction(user, amount, transferType ,bankAccount.getAccountId());
            bankTransactionService.saveBankTransaction(bankTransaction);
            bankAccountService.bankTransfer(bankAccount, amount);
            userService.subtractMoney(user, amount);
            bankAccounts = bankAccountService.getUserBankAccounts(user);
            mav.addObject("bankAccounts", bankAccounts);
            mav.addObject("success", "successful transfer");
            return mav;
        }
        else if(transferType.contains("account")) {
            logger.info("From user: " + user.getEmail() + "to the bank account : " + accountType + ", amount: " + amount);
            BankTransaction bankTransaction = bankTransactionService.newBankTransaction(user, amount, transferType, bankAccount.getAccountId());
            bankTransactionService.saveBankTransaction(bankTransaction);
            bankAccountService.accountTransfer(bankAccount, amount);
            userService.addMoney(user, amount);
            bankAccounts = bankAccountService.getUserBankAccounts(user);
            mav.addObject("bankAccounts", bankAccounts);
            mav.addObject("success", "successful transfer");
            return mav;
        }
        else {
            logger.info("Error: the user don't have enough money ");
            mav.addObject("error", "You don't have enough money ");
            return mav;
        }
    }


}

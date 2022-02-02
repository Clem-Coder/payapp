package com.paymybudy.payapp.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import static com.paymybudy.payapp.constants.Constants.PAY_MY_BUDDY_COMMISSION;

@Service
public class PayMyBuddyService {

    /**
     * here is the method to allow pay my buddy to take a 5% commission on all friend transaction
     */

    private static final Logger logger = LogManager.getLogger("PayMyBuddyService");

    public double takeTransactionCommission(double transactionAmount){
        double commission = transactionAmount * PAY_MY_BUDDY_COMMISSION;
        logger.info("Commission for pay my buddy: " + commission);
        transactionAmount = Math.round((transactionAmount - commission )*100)/100.0;
        return transactionAmount;
    }
}

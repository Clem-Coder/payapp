package com.paymybudy.payapp.repository;

import com.paymybudy.payapp.model.FriendTransaction;
import com.paymybudy.payapp.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FriendTransactionRepository extends JpaRepository<FriendTransaction, Integer> {

    /**
     * here are all the methods used to access and manipulate datas in FriendTransaction table
     */

    List<FriendTransaction> getFriendTransactionByDebtor(User user);
    Page<FriendTransaction> getFriendTransactionByDebtorOrCreditorId(User user, int creditorId, Pageable pageable);
}

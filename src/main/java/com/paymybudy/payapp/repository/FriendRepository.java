package com.paymybudy.payapp.repository;

import com.paymybudy.payapp.model.Friend;
import com.paymybudy.payapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FriendRepository extends JpaRepository <Friend, String> {

    /**
     * here are all the methods used to access and manipulate datas in Friend table
     */


    Friend findByEmailAndUser(String email, User user);

    List<Friend> findByUser(User user);
}

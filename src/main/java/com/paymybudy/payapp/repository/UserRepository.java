package com.paymybudy.payapp.repository;

import com.paymybudy.payapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository <User,Integer> {

    /**
     * here are all the methods used to access and manipulate datas in User table
     */

    List<User> findByEmailAndPassword(String email, String password);
    List<User> findByEmail(String email);
    List<User> findAll();
    User findByFirstNameAndLastName(String firstName, String lastName);
}

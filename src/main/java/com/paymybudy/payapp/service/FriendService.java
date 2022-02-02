package com.paymybudy.payapp.service;

import com.paymybudy.payapp.model.Friend;
import com.paymybudy.payapp.model.User;
import com.paymybudy.payapp.repository.FriendRepository;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class FriendService {

    /**
     * here are all the methods use to manipulate (Create,read, update & delete) datas from Friend table
     */

    private static final Logger logger = LogManager.getLogger("FriendService");

    private final UserService userService;

    private final FriendRepository friendRepository;

    public List<Friend> getUserFriendsList(User user) {
        logger.info("New request: get the friend list of the user: " + user.getEmail());
        return friendRepository.findByUser(user);
    }

    public void saveFriend (Friend friend){
        logger.info("Add a new friend: " + friend.getEmail() + " in database");
        friendRepository.save(friend);
    }

    public List<String> getFriendsEmails(List<Friend> friends){
        logger.info("New request: get friends email by a friend list");
        List<String> friendsEmails = new ArrayList<>();
        for (Friend friend : friends){
            friendsEmails.add(friend.getEmail());
        }
        return friendsEmails;
    }

    public Friend createFriendByEmail(String emailAddress, User user) {
        logger.info("create a new friend: " + emailAddress + ", for the user " + user.getEmail());
        Friend friend = new Friend();
        List<User> userList = userService.findByEmail(emailAddress);
        User userInList = userList.get(0);
        friend.setEmail(userInList.getEmail());
        friend.setFirstName(userInList.getFirstName());
        friend.setLastName(userInList.getLastName());
        friend.setUser(user);
        return friend;
    }
}



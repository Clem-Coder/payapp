package com.paymybudy.payapp.controller;

import com.paymybudy.payapp.model.Friend;
import com.paymybudy.payapp.model.User;
import com.paymybudy.payapp.service.FriendService;
import com.paymybudy.payapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import java.util.List;


@SessionAttributes("user")
@Controller
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {

    /**
     * here are all the methods to show the template to add a new connection and to add a new connection,
     * also the methods to show the user profile and to change the user password
     */

    private static final Logger logger = LogManager.getLogger("UserController");

    private final UserService userService;

    private final FriendService friendService;

    private final PasswordEncoder passwordEncoder;



    @GetMapping("/addConnectionForm")
    public ModelAndView addConnectionForm(@ModelAttribute("user") User user) {
        logger.info("New request: show the add-buddy template in the view");
        ModelAndView mav = new ModelAndView("add-buddy");
        List<Friend> friends = friendService.getUserFriendsList(user);
        mav.addObject("friends", friends);
        List<String> friendsEmails = friendService.getFriendsEmails(friends);
        mav.addObject("friendsEmails", friendsEmails);
        return mav;
    }

    @PostMapping ("/addConnection")
    public ModelAndView addConnection(@RequestParam String addressMail, @ModelAttribute("user") User user) {
        logger.info("New request: the user: " + user.getEmail() + " try to add a new connection");
        ModelAndView mav = new ModelAndView("add-buddy");
        mav.addObject("previousSearch", addressMail);
        List<String> usersEmails = userService.getUsersEmail();
        mav.addObject("usersEmails", usersEmails);
        List<Friend> friends = user.getFriendsList();
        mav.addObject("friends", friends);
        List<String> friendsEmails = friendService.getFriendsEmails(friends);
        List<User> userList = userService.findByEmail(addressMail);
        mav.addObject("friendsEmails", friendsEmails);
        if(userList.isEmpty()){
            logger.info("Error: any user with this email" );
            return mav;
        }
        else if(user.getEmail().equalsIgnoreCase(addressMail))
        {
            logger.info("Error: the user can't add himself in friend" );
            return mav;
        }

        else if(friendsEmails.contains(addressMail))
        {
            logger.info("Error: the user already know this connection" );
            return mav;
        }
        else{
            logger.info("Success: the user: " + user.getEmail() + " , added: " + addressMail + " in his friends" );
            Friend friend = friendService.createFriendByEmail(addressMail, user);
            friendService.saveFriend(friend);
            friends.add(friend);
            mav.addObject("successMessage", "Buddy successfully  added");
            return mav;
        }
    }

    @GetMapping("/profile")
    public ModelAndView profilForm(@ModelAttribute("user") User user) {
        logger.info("New request: show the profile-form template in the view");
        ModelAndView mav = new ModelAndView("profile-form");
        return mav;
    }

    @GetMapping("/changePasswordForm")
    public ModelAndView changePasswordForm(@ModelAttribute("user") User user) {
        logger.info("New request: show the change-password-form template in the view");
        ModelAndView mav = new ModelAndView("change-password-form");
        return mav;
    }

    @PostMapping("/changePassword")
    public ModelAndView changePassword(@ModelAttribute("user") User user, @RequestParam String password, @RequestParam String confirmPassword) {
        logger.info("New request: the user: " + user.getEmail() + " try change his password");
        ModelAndView mav = new ModelAndView("change-password-form");
        if(password.isEmpty()){
            logger.info("Error: the user didnt enter his new password");
            mav.addObject("error", "Please Enter a new password");
            return mav;
        }
        if(!password.isEmpty() && confirmPassword.isEmpty()){
            logger.info("Error: the user didnt confirm his new password");
            mav.addObject("error", "Please confirm your new password");
            return mav;
        }
        if(!password.equalsIgnoreCase(confirmPassword)){
            logger.info("Error: the user didnt enter the same values for the password and the confirm password");
            mav.addObject("error", "Enter values are not the same");
            return mav;
        }
        else{
            logger.info("Success: password successfully changed");
            user.setPassword(passwordEncoder.encode(password));
            userService.saveUser(user);
            mav.addObject("success", "Password successfully changed");
            return mav;
        }
    }
}




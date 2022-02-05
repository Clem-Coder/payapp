package com.paymybudy.payapp.controller;

import com.paymybudy.payapp.model.User;
import com.paymybudy.payapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * here are all the methods to show the registration page, and to allow new users to register
 */

@Controller
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RegistrationController {

    private final UserService userService;

    /**
     * Show the registration page
     *
     * @return the registration template
     */
    @GetMapping("/registrationForm")
    public ModelAndView signUpForm(){
        ModelAndView mav = new ModelAndView("registration-form");
        return mav;
    }


    /**
     *This method allow new users to register
     *it takes three parameters : An amount, a friend email and a comment.
     * The new user have to enter all those informations about him : firsname, lastname, email and password.
     * he also have to enter the same password two time. if both password entered are not the same, the method failed,
     * and if one of the information is missing, the method failed.
     *
     * @param firstName the firstname of the new user
     * @param lastName the lastName of the new user
     * @param email the email of the new user
     * @param password  the password of the new user
     * @param confirmPassword the confirmPassword of the new user (have to be exactly the same as the password)
     * @param role the role of the new user (in this case the role will always be "user")
     * @param enabled boolean value: 1 if the user is enabled, 0 if he is not (in this case, the user will always be anabled)
     */
    @PostMapping("/registration")
    public ModelAndView signUp(@RequestParam String firstName, @RequestParam String lastName, @RequestParam String email,
                               @RequestParam String password, @RequestParam String confirmPassword, @RequestParam String role, @RequestParam int enabled ) throws Exception{
    ModelAndView mavError = new ModelAndView("registration-form");
    ModelAndView mavSuccess = new ModelAndView("sign-up-and-delete-success");
    List<User> userList = userService.getUsers();
    for(User user : userList)
        if (user.getEmail().equalsIgnoreCase(email)){
            mavError.addObject("emailError", "A user with this email is already know");
            return mavError;
        }
    if(firstName.isEmpty()){
        mavError.addObject("firstNameError", "Please enter your firstname");
        return mavError;
    }
    if(lastName.isEmpty()){
        mavError.addObject("lastNameError", "Please enter your lastname");
        return mavError;
    }
    if(email.isEmpty()){
        mavError.addObject("emailError", "Please enter your email address");
        return mavError;
    }
    if(password.isEmpty()){
        mavError.addObject("passwordError", "Please enter your password");
        return mavError;
    }
    if(confirmPassword.isEmpty()){
        mavError.addObject("passwordError", "Please confirm your password");
        return mavError;
    }
    if(!password.equalsIgnoreCase(confirmPassword)){
        mavError.addObject("passwordError", "Passwords are not the same");
        return mavError;
    }
    else{
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        password = passwordEncoder.encode(password);
        user.setPassword(password);
        user.setRole(role);
        user.setEnabled(enabled);

        userService.saveUser(user);
        mavSuccess.addObject("created", "Your account has been successfully created ✓");
        return  mavSuccess;
    }
    }


}

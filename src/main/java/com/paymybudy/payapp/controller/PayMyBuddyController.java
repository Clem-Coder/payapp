package com.paymybudy.payapp.controller;

import com.paymybudy.payapp.model.User;
import com.paymybudy.payapp.repository.UserRepository;
import com.paymybudy.payapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * here are all the methods to show the login page, the homepage and the contact page
 **/

@Controller
@SessionAttributes("user")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PayMyBuddyController {

    private static final Logger logger = LogManager.getLogger("PayMyBuddyController");

    private final UserRepository userRepository;

    private final UserService userService;


    /**
     * Show the login page
     *
     * @return the login template
     */
    @GetMapping("/login")
    public ModelAndView accessForm(){
        logger.info("New request: show the login (access) template in the view ");
        ModelAndView mav = new ModelAndView("access");
        return mav;
    }

    /**
     * Show the homepage
     *
     * @param httpServletRequest contain all the information about the logged user (email and password)
     * @return the homepage template
     */
    @GetMapping({"/","/homepage"})
    public ModelAndView homepageForm(HttpServletRequest httpServletRequest){
        logger.info("New request: show the homepage template in the view ");
        User user = userService.getLoggedUser(httpServletRequest);
        ModelAndView mav = new ModelAndView("homepage");
        mav.addObject("user", user);
        return mav;
    }

    /**
     * Show the contact page
     *
     * @return the contact template
     */
    @GetMapping("/contact")
    public ModelAndView contact(){
        logger.info("New request: show the contact template in the view ");
        ModelAndView mav = new ModelAndView("contact");
        return mav;
    }

}

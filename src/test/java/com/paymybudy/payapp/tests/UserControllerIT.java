package com.paymybudy.payapp.tests;

import com.paymybudy.payapp.controller.UserController;
import com.paymybudy.payapp.model.*;
import com.paymybudy.payapp.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.transaction.Transactional;
import java.util.List;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@AutoConfigureMockMvc
@Transactional
public class UserControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MockHttpSession session;

    @Autowired
    WebApplicationContext context;

    @Autowired
    UserController userController;

    @Autowired
    UserService userService;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
        List<User> userList = userService.findByEmail("pauline.germain@gmail.com");
        User user = userList.get(0);
        session.setAttribute("user",user);
    }


    @Test
    @WithMockUser()
    public void addConnectionFormTest() throws Exception{
        mockMvc.perform(get("/addConnectionForm").session(session)).andExpect(status().isOk());
    }


    @Test
    @WithMockUser()
    public void addConnectionTest() throws Exception{
        mockMvc.perform(post("/addConnection").session(session).param("addressMail","anthony.diurne@gmail.com")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser()
    public void addConnection_WithUserEmailErrorTest() throws Exception{
        mockMvc.perform(post("/addConnection").session(session).param("addressMail","pauline.germain@gmail.com")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser()
    public void addConnection_WithAlreadyKnowErrorTest() throws Exception{
        mockMvc.perform(post("/addConnection").session(session).param("addressMail","john.doe@gmail.com")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser()
    public void addConnection_WithUnknownUserErrorTest() throws Exception{
        mockMvc.perform(post("/addConnection").session(session).param("addressMail","test.test@gmail.com\"")).andExpect(status().isOk());
    }


    @Test
    @WithMockUser()
    public void profileTest() throws Exception{
        mockMvc.perform(get("/profile").session(session)).andExpect(status().isOk());
    }

    @Test
    @WithMockUser()
    public void changePasswordFormTest() throws Exception{
        mockMvc.perform(get("/changePasswordForm").session(session)).andExpect(status().isOk());
    }

    @Test
    @WithMockUser()
    public void changePasswordTest() throws Exception{
        mockMvc.perform(post("/changePassword").session(session).param("password","test").param("confirmPassword", "test")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser()
    public void changePasswordWithEmptyPassword_ErrorTest() throws Exception{
        mockMvc.perform(post("/changePassword").session(session).param("password","").param("confirmPassword", "test")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser()
    public void changePasswordWithEmptyConfirmPassword_ErrorTest() throws Exception{
        mockMvc.perform(post("/changePassword").session(session).param("password","test").param("confirmPassword", "")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser()
    public void changePasswordWithEmptyPasswordAndEmptyConfirmPassword_ErrorTest() throws Exception{
        mockMvc.perform(post("/changePassword").session(session).param("password","").param("confirmPassword", "")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser()
    public void changePasswordWithDifferentPasswords_ErrorTest() throws Exception{
        mockMvc.perform(post("/changePassword").session(session).param("password","test").param("confirmPassword", "tset")).andExpect(status().isOk());
    }



}

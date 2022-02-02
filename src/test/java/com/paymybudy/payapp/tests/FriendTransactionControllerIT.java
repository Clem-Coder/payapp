package com.paymybudy.payapp.tests;

import com.paymybudy.payapp.controller.UserController;
import com.paymybudy.payapp.model.User;
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
public class FriendTransactionControllerIT {

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
    public void transferFormTest() throws Exception{
        mockMvc.perform(get("/transferForm").session(session)).andExpect(status().isOk());
    }

    @Test
    @WithMockUser()
    public void transferFormWithLowTotalPageTest() throws Exception{
        List<User> userList = userService.findByEmail("john.doe@gmail.com");
        User user = userList.get(0);
        session.setAttribute("user",user);
        mockMvc.perform(get("/transferForm").session(session)).andExpect(status().isOk());
    }


    @Test
    @WithMockUser()
    public void transferPageTest() throws Exception{
        mockMvc.perform(get("/transferPage").session(session).param("pageNumber","0")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser()
    public void transferPageWithLowTotalPageTest() throws Exception{
        List<User> userList = userService.findByEmail("john.doe@gmail.com");
        User user = userList.get(0);
        session.setAttribute("user",user);
        mockMvc.perform(get("/transferPage").session(session).param("pageNumber","0")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser() // true - true
    public void transferTest() throws Exception{
        mockMvc.perform(post("/transfer").session(session).param("friendEmail","john.doe@gmail.com")
                .param("amount", "10").param("comment", "test")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser()
    public void transferWithLowTotalPageTest() throws Exception{
        List<User> userList = userService.findByEmail("john.doe@gmail.com");
        User user = userList.get(0);
        session.setAttribute("user",user);
        mockMvc.perform(post("/transfer").session(session).param("friendEmail","john.doe@gmail.com")
                .param("amount", "10").param("comment", "test")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser()
    public void transferWithNoFriend_ErrorTest() throws Exception{
        mockMvc.perform(post("/transfer").session(session).param("friendEmail","select a connection")
                .param("amount", "10").param("comment", "test")).andExpect(status().isOk());
    }


    @Test
    @WithMockUser() // false - true
    public void transferWithUserBalance_ErrorTest() throws Exception{
        mockMvc.perform(post("/transfer").session(session).param("friendEmail","john.doe@gmail.com")
                .param("amount", "10000").param("comment", "test")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser() // false - true
    public void transferWithAmount_ErrorTest() throws Exception{
        mockMvc.perform(post("/transfer").session(session).param("friendEmail","john.doe@gmail.com")
                .param("amount", "0").param("comment", "test")).andExpect(status().isOk());
    }

}

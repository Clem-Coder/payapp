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

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@AutoConfigureMockMvc
@Transactional
public class PayMyBuddyControllerIT {

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
    public void shouldReturnDefaultMessage() throws Exception {
        mockMvc.perform(get("/login")).andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void userLoginTest() throws Exception{
        mockMvc.perform(formLogin("/login").user("pauline.germain@gmail.com").password("root1")).andExpect(authenticated());
    }

    @Test
    public void userLoginErrorTest() throws Exception{
        mockMvc.perform(formLogin("/login").user("pauline.germain@gmail.com").password("root")).andExpect(unauthenticated());
    }

    @Test
    @WithMockUser(username = "pauline.germain@gmail.com")
    public void homepageTest() throws Exception{
        mockMvc.perform(get("/")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser()
    public void contactTest() throws Exception{
        mockMvc.perform(get("/contact")).andExpect(status().isOk());
    }


}

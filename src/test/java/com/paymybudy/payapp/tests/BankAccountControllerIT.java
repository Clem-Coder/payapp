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
public class BankAccountControllerIT {

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
    public void addBankAccountFormTest() throws Exception{
        mockMvc.perform(get("/addBankAccountForm").session(session)).andExpect(status().isOk());
    }

    @Test // true true
    @WithMockUser()
    public void addBankAccountTest() throws Exception{
        mockMvc.perform(post("/addBankAccount").session(session).param("accountNumber", "FR32_XXXXX_XXXXX_XXXXXXXXXX_76")
                .param("accountType", "Savings account")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser()
    public void addBankAccountWithAccountType_ErrorTest() throws Exception{
        mockMvc.perform(post("/addBankAccount").session(session).param("accountNumber", "FR32_XXXXX_XXXXX_XXXXXXXXXX_45")
                .param("accountType", "Compte courant")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser() // false true
    public void addBankAccountWithBankAccountNumber_ErrorTest() throws Exception{
        mockMvc.perform(post("/addBankAccount").session(session).param("accountNumber", "FR32_XXXXX_XXXXX_XXXXXXXXXX_4789")
                .param("accountType", "Compte courant")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser() // true false
    public void addBankAccountWithoutAccountType_ErrorTest() throws Exception{
        mockMvc.perform(post("/addBankAccount").session(session).param("accountNumber", "FR32_XXXXX_XXXXX_XXXXXXXXXX_45")
                .param("accountType", "")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser()
    public void addBankAccountWithoutBankAccount_ErrorTest() throws Exception{
        mockMvc.perform(post("/addBankAccount").session(session).param("accountNumber", "")
                .param("accountType", "Savings account")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser() // false false
    public void addBankAccountWithoutAccountTypeAndWithoutBankAccount_ErrorTest() throws Exception{
        mockMvc.perform(post("/addBankAccount").session(session).param("accountNumber", "FR32_XXXXX_XXXXX_XXXXXXXXXX_4789")
                .param("accountType", "")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser()
    public void addBankAccountWithAlreadyUseBankAccount_ErrorTest() throws Exception{
        mockMvc.perform(post("/addBankAccount").session(session).param("accountNumber", "FR32_XXXXX_XXXXX_XXXXXXXXXX_59")
                .param("accountType", "Savings account")).andExpect(status().isOk());
    }

}


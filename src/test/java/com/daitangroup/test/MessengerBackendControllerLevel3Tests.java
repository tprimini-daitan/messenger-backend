package com.daitangroup.test;

import com.daitangroup.controllers.types.ResponseContent;
import com.daitangroup.entity.User;
import com.daitangroup.repo.UserRepository;
import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class MessengerBackendControllerLevel3Tests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean UserRepository userRepository;

    @Before
    public void prepareFindUserByName() {

    }

    @Test
    public void testEndpointFindUserByNameReturnNullList() {
        when(userRepository.findByName("user1")).thenReturn(null);

        try {
            this.mockMvc.perform(get("/lm_3/messenger/user/user1/name"))
                    .andExpect(status().isNotFound())
                    .andReturn();
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void testEndpointFindUserByNameReturnEmptyList() {
        try {
            this.mockMvc.perform(get("/lm_3/messenger/user/userX/name"))
                    .andExpect(status().isNotFound())
                    .andReturn();
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void testEndpointFindUserByNameOnlyOneUserInDatabase() {
        when(userRepository.findByName("user1")).thenReturn(Arrays.asList(new User("1", "user1", "pass1")));

        try {
            MvcResult mvcResult = this.mockMvc.perform(get("/lm_3/messenger/user/user1/name"))
                    .andExpect(status().isOk())
                    .andReturn();
            Gson gson = new Gson();
            ResponseContent resultUserList = gson.fromJson(mvcResult.getResponse().getContentAsString(), ResponseContent.class);
            assertEquals("1", resultUserList.getUsers().get(0).getId());
            assertEquals("user1", resultUserList.getUsers().get(0).getName());
            assertEquals("pass1", resultUserList.getUsers().get(0).getPassword());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void testEndpointFindUserByNameOnlyMultiUsersInDatabase() {
        when(userRepository.findByName("user0")).thenReturn(Arrays.asList(new User("0", "user0", "pass0")));
        when(userRepository.findByName("user1")).thenReturn(Arrays.asList(new User("1", "user1", "pass1")));
        when(userRepository.findByName("user2")).thenReturn(Arrays.asList(new User("2", "user2", "pass2")));

        try {
            MvcResult mvcResult = this.mockMvc.perform(get("/lm_3/messenger/user/user1/name"))
                    .andExpect(status().isOk())
                    .andReturn();
            Gson gson = new Gson();
            ResponseContent resultUserList = gson.fromJson(mvcResult.getResponse().getContentAsString(), ResponseContent.class);
            assertEquals("1", resultUserList.getUsers().get(0).getId());
            assertEquals("user1", resultUserList.getUsers().get(0).getName());
            assertEquals("pass1", resultUserList.getUsers().get(0).getPassword());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

}
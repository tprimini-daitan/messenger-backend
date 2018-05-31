package com.daitangroup.controllers;

import com.daitangroup.ResponseContent;
import com.daitangroup.entity.User;
import com.daitangroup.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.web.bind.annotation.RequestMethod.POST;


@RestController
public class MessengerBackendControllerLevel1 {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value="lm_1/messenger/create", method=POST)
    @ResponseBody
    public ResponseContent createUser(@RequestParam(name="name", required=false, defaultValue="") String name,
                                      @RequestParam(name="password", required=false, defaultValue="") String password) {

        List<User> users = new ArrayList<User>();

        User user = new User();

        user.setName(name);
        user.setPassword(password);

        ResponseContent responseContent = new ResponseContent();

        try {
            User addedUser = userRepository.insert(user);
            responseContent.setService("create");
            users.add(addedUser);
            responseContent.setUsers(users);
        } catch (Exception e) {
            responseContent.setService(e.toString());
        }

        return responseContent;
    }

    @RequestMapping(value="lm_1/messenger/read", method=POST)
    @ResponseBody
    public ResponseContent readUser(@RequestParam(name="id", required=false) String id) {

        List<User> users = new ArrayList<User>();

        ResponseContent responseContent = new ResponseContent();

        try {
            if (id != null) {
                Optional<User> gotUser = userRepository.findById(id);
                users.add(gotUser.get());
                responseContent.setUsers(users);
            } else {
                List gotUsers = userRepository.findAll();
                responseContent.setUsers(gotUsers);
            }
            responseContent.setService("read");
        } catch (Exception e) {
            responseContent.setService(e.toString());
        }

        return responseContent;
    }


    @RequestMapping(value="lm_1/messenger/update", method=POST)
    @ResponseBody
    public ResponseContent updateUser(@RequestParam(name="id") String id,
                                      @RequestParam(name="name", required=false, defaultValue="") String name,
                                      @RequestParam(name="password", required=false, defaultValue="") String password) {

        List<User> users = new ArrayList<User>();

        User user = new User();

        user.setName(name);
        user.setPassword(password);

        ResponseContent responseContent = new ResponseContent();

        try {
            user.setId(id);
            User updatedUser = userRepository.save(user);
            responseContent.setService("update");
            users.add(updatedUser);
            responseContent.setUsers(users);
        } catch (Exception e) {
            responseContent.setService(e.toString());
        }

        return responseContent;
    }

    @RequestMapping(value="lm_1/messenger/delete", method=POST)
    @ResponseBody
    public ResponseContent deleteUser(@RequestParam(name="id") String id) {

        List<User> users = new ArrayList<User>();

        User user = new User();

        ResponseContent responseContent = new ResponseContent();

        try {
            user.setId(id);
            userRepository.delete(user);
            responseContent.setService("delete");
            users.add(user);
            responseContent.setUsers(users);
        } catch (Exception e) {
            responseContent.setService(e.toString());
        }

        return responseContent;
    }
}
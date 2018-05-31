package com.daitangroup.controllers;

import com.daitangroup.ResponseContent;
import com.daitangroup.entity.User;
import com.daitangroup.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.web.bind.annotation.RequestMethod.POST;


@RestController
public class MessengerBackendControllerLevel0 {

    @Autowired
    private UserRepository userRepository;

    private static final String C_CREATE_SERVICE = "create";
    private static final String C_READ_SERVICE = "read";
    private static final String C_UPDATE_SERVICE = "update";
    private static final String C_DELETE_SERVICE = "delete";

    @RequestMapping(value="lm_0/messenger/userService", method=POST)
    @ResponseBody
    public ResponseContent doUserService(@RequestParam(name="service", defaultValue="create") String service,
                                         @RequestParam(name="id", required=false) String id,
                                         @RequestParam(name="name", required=false, defaultValue="") String name,
                                         @RequestParam(name="password", required=false, defaultValue="") String password) {

        List<User> users = new ArrayList<User>();

        User user = new User();

        user.setName(name);
        user.setPassword(password);

        ResponseContent responseContent = new ResponseContent();

        try {
            switch (service) {
                case C_CREATE_SERVICE:
                    User addedUser = userRepository.insert(user);
                    responseContent.setService(C_CREATE_SERVICE);
                    users.add(addedUser);
                    responseContent.setUsers(users);
                    break;
                case C_READ_SERVICE:
                    if (id != null) {
                        Optional<User> gotUser = userRepository.findById(id);
                        users.add(gotUser.get());
                        responseContent.setUsers(users);
                    } else {
                        List gotUsers = userRepository.findAll();
                        responseContent.setUsers(gotUsers);
                    }
                    responseContent.setService(C_READ_SERVICE);
                    break;
                case C_UPDATE_SERVICE:
                    user.setId(id);
                    User updatedUser = userRepository.save(user);
                    responseContent.setService(C_UPDATE_SERVICE);
                    users.add(updatedUser);
                    responseContent.setUsers(users);
                    break;
                case C_DELETE_SERVICE:
                    user.setId(id);
                    userRepository.delete(user);
                    responseContent.setService(C_DELETE_SERVICE);
                    users.add(user);
                    responseContent.setUsers(users);
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            responseContent.setService(e.toString());
        }

        return responseContent;
    }

}
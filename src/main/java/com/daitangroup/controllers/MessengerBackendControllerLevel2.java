package com.daitangroup.controllers;

import com.daitangroup.controllers.types.ResponseContent;
import com.daitangroup.entity.User;
import com.daitangroup.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;


@RestController
public class MessengerBackendControllerLevel2 {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value="lm_2/messenger/user", method=POST, produces="application/json")
    @ResponseBody
    public ResponseEntity createUser(@RequestParam(name="name", required=false, defaultValue="") String name,
                                     @RequestParam(name="password", required=false, defaultValue="") String password) {

        HttpStatus httpStatus = HttpStatus.CREATED;

        List<User> users = new ArrayList<User>();

        User user = new User(null, name, password);

        ResponseContent responseContent = new ResponseContent();

        try {
            User addedUser = userRepository.insert(user);
            responseContent.setService("create");
            users.add(addedUser);
            responseContent.setUsers(users);
        } catch (Exception e) {
            responseContent.setService(e.toString());
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return new ResponseEntity<>(responseContent, httpStatus);
    }

    @RequestMapping(value="lm_2/messenger/user", method=GET, produces="application/json")
    @ResponseBody
    public ResponseEntity readUser(@RequestParam(name="id", required=false) String id) {

        HttpStatus httpStatus = HttpStatus.OK;

        List<User> users = new ArrayList<User>();

        ResponseContent responseContent = new ResponseContent();

        try {
            if (id != null) {
                Optional<User> gotUser = userRepository.findById(id);
                users.add(gotUser.get());
                responseContent.setUsers(users);
                if (gotUser == null) {
                    httpStatus = HttpStatus.NOT_FOUND;
                }
            } else {
                List gotUsers = userRepository.findAll();
                responseContent.setUsers(gotUsers);
            }
            responseContent.setService("read");
        } catch (Exception e) {
            responseContent.setService(e.toString());
            httpStatus = HttpStatus.NOT_FOUND;
        }

        return new ResponseEntity<>(responseContent, httpStatus);
    }


    @RequestMapping(value="lm_2/messenger/user", method=PUT, produces="application/json")
    @ResponseBody
    public ResponseEntity updateUser(@RequestParam(name="id") String id,
                                      @RequestParam(name="name", required=false, defaultValue="") String name,
                                      @RequestParam(name="password", required=false, defaultValue="") String password) {

        HttpStatus httpStatus = HttpStatus.OK;

        List<User> users = new ArrayList<User>();

        User user = new User(id, name, password);

        ResponseContent responseContent = new ResponseContent();

        try {
            user.setId(id);
            User updatedUser = userRepository.save(user);
            responseContent.setService("update");
            users.add(updatedUser);
            responseContent.setUsers(users);
        } catch (Exception e) {
            responseContent.setService(e.toString());
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return new ResponseEntity<>(responseContent, httpStatus);
    }

    @RequestMapping(value="lm_2/messenger/user", method=DELETE, produces="application/json")
    @ResponseBody
    public ResponseEntity deleteUser(@RequestParam(name="id") String id) {

        HttpStatus httpStatus = HttpStatus.OK;

        List<User> users = new ArrayList<User>();

        User user = new User(id, null, null);

        ResponseContent responseContent = new ResponseContent();

        try {
            user.setId(id);
            userRepository.delete(user);
            responseContent.setService("delete");
            users.add(user);
            responseContent.setUsers(users);
        } catch (Exception e) {
            responseContent.setService(e.toString());
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return new ResponseEntity<>(responseContent, httpStatus);
    }
}
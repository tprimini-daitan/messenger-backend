package com.daitangroup.controllers;

import com.daitangroup.MessageRequestData;
import com.daitangroup.ResponseContent;
import com.daitangroup.entity.Message;
import com.daitangroup.entity.MessageTransmission;
import com.daitangroup.entity.User;
import com.daitangroup.repo.MessageRepository;
import com.daitangroup.repo.MessageTransmissionRepository;
import com.daitangroup.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.springframework.web.bind.annotation.RequestMethod.*;


@RestController
public class MessengerBackendControllerLevel3 {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private MessageTransmissionRepository messageTransmissionRepository;

    @RequestMapping(value="lm_3/messenger/user", method=POST, produces="application/json")
    @ResponseBody
    public ResponseEntity createUser(@RequestParam(name="name", required=false, defaultValue="") String name,
                                     @RequestParam(name="password", required=false, defaultValue="") String password) {

        HttpStatus httpStatus = HttpStatus.CREATED;

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
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        addLinksToContentForUser(responseContent, user);

        return new ResponseEntity<>(responseContent, httpStatus);
    }

    @RequestMapping(value="lm_3/messenger/user", method=GET, produces="application/json")
    @ResponseBody
    public ResponseEntity readUser(@RequestParam(name="id", required=false) String id) {

        HttpStatus httpStatus = HttpStatus.OK;

        List<User> users = new ArrayList<User>();

        ResponseContent responseContent = new ResponseContent();

        try {
            if (id != null) {
                Optional<User> gotUser = userRepository.findById(id);
                if (!gotUser.isPresent()) {
                    httpStatus = HttpStatus.NOT_FOUND;
                } else {
                    users.add(gotUser.get());
                }
                responseContent.setUsers(users);
            } else {
                List gotUsers = userRepository.findAll();
                responseContent.setUsers(gotUsers);
            }
            responseContent.setService("read");
        } catch (Exception e) {
            responseContent.setService(e.toString());
            httpStatus = HttpStatus.NOT_FOUND;
        }

        addLinksToContentForUser(responseContent, null);

        return new ResponseEntity<>(responseContent, httpStatus);
    }


    @RequestMapping(value="lm_3/messenger/user", method=PUT, produces="application/json")
    @ResponseBody
    public ResponseEntity updateUser(@RequestParam(name="id") String id,
                                      @RequestParam(name="name", required=false, defaultValue="") String name,
                                      @RequestParam(name="password", required=false, defaultValue="") String password) {

        HttpStatus httpStatus = HttpStatus.OK;

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
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        addLinksToContentForUser(responseContent, user);

        return new ResponseEntity<>(responseContent, httpStatus);
    }

    @RequestMapping(value="lm_3/messenger/user", method=DELETE, produces="application/json")
    @ResponseBody
    public ResponseEntity deleteUser(@RequestParam(name="id") String id) {

        HttpStatus httpStatus = HttpStatus.OK;

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
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        addLinksToContentForUser(responseContent, user);

        return new ResponseEntity<>(responseContent, httpStatus);
    }

    @RequestMapping(value="lm_3/messenger/send_message", method=POST, produces="application/json")
    @ResponseBody
    public ResponseEntity sendMessage(@RequestBody MessageRequestData messageRequestData) {

        ResponseContent responseContent = new ResponseContent();

        HttpStatus httpStatus = HttpStatus.OK;

        MessageTransmission messageTransmission = new MessageTransmission();

        if (messageRequestData.getSourceType().equals("user")) {
            Optional<User> sourceUser = userRepository.findById(messageRequestData.getSourceId());
            if (!sourceUser.isPresent()) {
                httpStatus = HttpStatus.NOT_FOUND;
                responseContent.setService("Source user not found");
                return new ResponseEntity<>(responseContent, httpStatus);
            }
            messageTransmission.setFromId(sourceUser.get().getId());
            messageTransmission.setFromType("user");
        }

        if (messageRequestData.getDestinationType().equals("user")) {
            Optional<User> destinationUser = userRepository.findById(messageRequestData.getDestinationId());
            if (!destinationUser.isPresent()) {
                httpStatus = HttpStatus.NOT_FOUND;
                responseContent.setService("Destination user not found");
                return new ResponseEntity<>(responseContent, httpStatus);
            }
            messageTransmission.setToId(destinationUser.get().getId());
            messageTransmission.setToType("user");
        }

        Message message = new Message();
        message.setPayload(messageRequestData.getMessagePayload());
        message.setCreatedAt(new Date());

        Message addedMessage = messageRepository.insert(message);

        messageTransmission.setMessageId(addedMessage.getId());

        messageTransmissionRepository.insert(messageTransmission);

        return new ResponseEntity<>(responseContent, httpStatus);
    }

    private void addLinksToContentForUser(ResponseContent responseContent, User user) {
        String id = null;
        String name;
        String password;

        if (user != null) {
            id = user.getId();
            name = user.getName();
            password = user.getPassword();
        } else {
            name = "username";
            password = "password";
        }

        responseContent.add(linkTo(methodOn(MessengerBackendControllerLevel3.class).createUser(name, password)).withSelfRel().withType("POST"));
        responseContent.add(linkTo(methodOn(MessengerBackendControllerLevel3.class).readUser(id)).withSelfRel().withType("GET"));
        responseContent.add(linkTo(methodOn(MessengerBackendControllerLevel3.class).updateUser(id, name, password)).withSelfRel().withType("PUT"));
        responseContent.add(linkTo(methodOn(MessengerBackendControllerLevel3.class).deleteUser(id)).withSelfRel().withType("DELETE"));
    }
}
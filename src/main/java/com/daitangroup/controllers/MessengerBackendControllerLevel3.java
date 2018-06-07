package com.daitangroup.controllers;

import com.daitangroup.service.types.MessageRequestData;
import com.daitangroup.controllers.types.ResponseContent;
import com.daitangroup.entity.Group;
import com.daitangroup.entity.User;
import com.daitangroup.service.CommunicationService;
import com.daitangroup.service.GroupService;
import com.daitangroup.service.UserService;
import com.daitangroup.service.types.CommunicationServiceStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import java.util.*;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.web.bind.annotation.RequestMethod.*;


@RestController
@RequestMapping(value="/lm_3/messenger")
public class MessengerBackendControllerLevel3 {

    private final UserService userService;
    private final GroupService groupService;
    private final CommunicationService communicationService;

    public MessengerBackendControllerLevel3(UserService userService,
                                            GroupService groupService,
                                            CommunicationService communicationService) {
        this.userService = userService;
        this.groupService = groupService;
        this.communicationService = communicationService;
    }

    @RequestMapping(value="/user", method=POST, produces="application/json")
    public ResponseEntity createUser(@RequestParam(name="name") String name,
                                     @RequestParam(name="password") String password) {

        User userToBeAdded = new User(null, name, password);

        User addedUser = userService.insert(userToBeAdded);

        if (addedUser == null) {
            return new ResponseEntity<>(null, INTERNAL_SERVER_ERROR);
        } else {
            ResponseContent responseContent = new ResponseContent();
            addLinksToContentForUser(responseContent, addedUser, null);
            responseContent.setUsers(Arrays.asList(addedUser));
            return new ResponseEntity<>(responseContent, CREATED);
        }
    }

    @RequestMapping(value="/user/{id}/id", method=GET, produces="application/json")
    public ResponseEntity readUserById(@PathVariable String id) {

        User user = userService.findById(id);

        if (user == null) {
            return new ResponseEntity<>(null, NOT_FOUND);
        } else {
            ResponseContent responseContent = new ResponseContent();
            addLinksToContentForUser(responseContent, user, null);
            responseContent.setUsers(Arrays.asList(user));
            return new ResponseEntity<>(responseContent, OK);
        }
    }

    @RequestMapping(value="/user/{name}/name", method=GET, produces="application/json")
    public ResponseEntity readUserByName(@PathVariable String name) {

        List<User> users = userService.findByName(name);

        if (users == null || users.size() == 0) {
            return new ResponseEntity<>(null, NOT_FOUND);
        } else {
            ResponseContent responseContent = new ResponseContent();
            addLinksToContentForUser(responseContent, null, null);
            responseContent.setUsers(users);
            return new ResponseEntity<>(responseContent, OK);
        }
    }

    @RequestMapping(value="/user", method=GET, produces="application/json")
    public ResponseEntity readAllUsers() {

        List<User> users = userService.findAll();

        if (users == null) {
            return new ResponseEntity<>(null, NOT_FOUND);
        } else {
            ResponseContent responseContent = new ResponseContent();
            addLinksToContentForUser(responseContent, null, null);
            responseContent.setUsers(users);
            return new ResponseEntity<>(responseContent, OK);
        }
    }

    @RequestMapping(value="/user", method=PUT, produces="application/json")
    public ResponseEntity updateUser(@RequestParam(name="id") String id,
                                     @RequestParam(name="name") String name,
                                     @RequestParam(name="password") String password) {

        User userToBeUpdated = new User(id, name, password);

        User updatedUser = userService.update(userToBeUpdated);

        if (updatedUser == null) {
            return new ResponseEntity<>(null, INTERNAL_SERVER_ERROR);
        } else {
            ResponseContent responseContent = new ResponseContent();
            addLinksToContentForUser(responseContent, updatedUser, null);
            responseContent.setUsers(Arrays.asList(updatedUser));
            return new ResponseEntity<>(responseContent, OK);
        }
    }

    @RequestMapping(value="/user", method=DELETE, produces="application/json")
    public ResponseEntity deleteUser(@RequestParam(name="id") String id) {


        if (!userService.delete(id)) {
            return new ResponseEntity<>(null, NOT_FOUND);
        } else {
            ResponseContent responseContent = new ResponseContent();
            addLinksToContentForUser(responseContent, null, null);
            return new ResponseEntity<>(responseContent, OK);
        }
    }

    @RequestMapping(value="/group", method=POST, produces="application/json")
    public ResponseEntity createGroup(@RequestBody Group group) {

        if (group == null) {
            return new ResponseEntity<>(null, BAD_REQUEST);
        }

        Group addedGroup = groupService.insert(group);

        if (addedGroup == null) {
            return new ResponseEntity<>(null, INTERNAL_SERVER_ERROR);
        } else {
            ResponseContent responseContent = new ResponseContent();
            responseContent.setGroups(Arrays.asList(addedGroup));
            addLinksToContentForUser(responseContent, null, group);
            return new ResponseEntity<>(responseContent, CREATED);
        }
    }

    @RequestMapping(value="/group", method=GET, produces="application/json")
    public ResponseEntity readAllGroups() {

        List<Group> groups = groupService.findAll();

        if (groups == null) {
            return new ResponseEntity<>(null, INTERNAL_SERVER_ERROR);
        } else {
            ResponseContent responseContent = new ResponseContent();
            addLinksToContentForUser(responseContent, null, null);
            responseContent.setGroups(groups);
            return new ResponseEntity<>(responseContent, CREATED);
        }
    }

    @RequestMapping(value="/send_message", method=POST, produces="application/json")
    public ResponseEntity sendMessage(@RequestBody MessageRequestData messageRequestData) {

        if (messageRequestData == null) {
            return new ResponseEntity<>(null, BAD_REQUEST);
        }

        CommunicationServiceStatus communicationServiceStatus = communicationService.sendMessage(messageRequestData);

        switch (communicationServiceStatus) {
            case DST_GROUP_NOT_FOUND:
            case DST_USER_NOT_FOUND:
            case SRC_GROUP_NOT_FOUND:
            case SRC_USER_NOT_FOUND:
                return new ResponseEntity<>(null, NOT_FOUND);
            case ERROR_CREATING_MSG:
            case ERROR_SENDING_MSG:
                return new ResponseEntity<>(null, INTERNAL_SERVER_ERROR);
            case OK:
                return new ResponseEntity<>(null, OK);
            default:
                return new ResponseEntity<>(null, INTERNAL_SERVER_ERROR);
        }
    }

    private void addLinksToContentForUser(ResponseContent responseContent, User user, Group group) {
        String userId = null;
        String userName;
        String password;

        if (user != null) {
            userId = user.getId();
            userName = user.getName();
            password = user.getPassword();
        } else {
            userName = "username";
            password = "password";
        }

        responseContent.add(linkTo(methodOn(MessengerBackendControllerLevel3.class).createUser(userName, password)).withSelfRel().withType("POST"));
        responseContent.add(linkTo(methodOn(MessengerBackendControllerLevel3.class).readUserById(userId)).withSelfRel().withType("GET"));
        responseContent.add(linkTo(methodOn(MessengerBackendControllerLevel3.class).readUserByName(userName)).withSelfRel().withType("GET"));
        responseContent.add(linkTo(methodOn(MessengerBackendControllerLevel3.class).readAllUsers()).withSelfRel().withType("GET"));
        responseContent.add(linkTo(methodOn(MessengerBackendControllerLevel3.class).updateUser(userId, userName, password)).withSelfRel().withType("PUT"));
        responseContent.add(linkTo(methodOn(MessengerBackendControllerLevel3.class).deleteUser(userId)).withSelfRel().withType("DELETE"));
        responseContent.add(linkTo(methodOn(MessengerBackendControllerLevel3.class).createGroup(group)).withSelfRel().withType("POST"));
        responseContent.add(linkTo(methodOn(MessengerBackendControllerLevel3.class).readAllGroups()).withSelfRel().withType("GET"));
        responseContent.add(linkTo(methodOn(MessengerBackendControllerLevel3.class).sendMessage(null)).withSelfRel().withType("POST"));
    }
}
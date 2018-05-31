package com.daitangroup.controllers;

import com.daitangroup.ResponseContent;
import com.daitangroup.User;
import com.daitangroup.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.POST;


@RestController
public class MessengerBackendControllerLevel1 {

    @Autowired
    @Qualifier("UserDaoMysqlImpl")
    private UserDao userDao;

    @RequestMapping(value="/messenger/create", method=POST)
    @ResponseBody
    public ResponseContent createUser(@RequestParam(name="name", required=false, defaultValue="") String name,
                                      @RequestParam(name="password", required=false, defaultValue="") String password) {

        List<User> users = new ArrayList<User>();

        User user = new User();

        user.setName(name);
        user.setPassword(password);

        ResponseContent responseContent = new ResponseContent();

        try {
            userDao.create(user);
            responseContent.setService("create");
            users.add(user);
            responseContent.setUsers(users);
        } catch (Exception e) {
            responseContent.setService(e.toString());
        }

        return responseContent;
    }

    @RequestMapping(value="/messenger/read", method=POST)
    @ResponseBody
    public ResponseContent readUser(@RequestParam(name="id", required=false) Integer id) {

        List<User> users = new ArrayList<User>();

        ResponseContent responseContent = new ResponseContent();

        try {
            if (id != null) {
                User gotUser = userDao.getById(id);
                users.add(gotUser);
                responseContent.setUsers(users);
            } else {
                List gotUsers = userDao.getAll();
                responseContent.setUsers(gotUsers);
            }
            responseContent.setService("read");
        } catch (Exception e) {
            responseContent.setService(e.toString());
        }

        return responseContent;
    }


    @RequestMapping(value="/messenger/update", method=POST)
    @ResponseBody
    public ResponseContent updateUser(@RequestParam(name="id") Integer id,
                                      @RequestParam(name="name", required=false, defaultValue="") String name,
                                      @RequestParam(name="password", required=false, defaultValue="") String password) {

        List<User> users = new ArrayList<User>();

        User user = new User();

        user.setName(name);
        user.setPassword(password);

        ResponseContent responseContent = new ResponseContent();

        try {
            user.setId(id);
            userDao.update(user);
            responseContent.setService("update");
            users.add(user);
            responseContent.setUsers(users);
        } catch (Exception e) {
            responseContent.setService(e.toString());
        }

        return responseContent;
    }

    @RequestMapping(value="/messenger/delete", method=POST)
    @ResponseBody
    public ResponseContent deleteUser(@RequestParam(name="id") Integer id) {

        List<User> users = new ArrayList<User>();

        User user = new User();

        ResponseContent responseContent = new ResponseContent();

        try {
            user.setId(id);
            userDao.delete(user);
            responseContent.setService("delete");
            users.add(user);
            responseContent.setUsers(users);
        } catch (Exception e) {
            responseContent.setService(e.toString());
        }

        return responseContent;
    }
}
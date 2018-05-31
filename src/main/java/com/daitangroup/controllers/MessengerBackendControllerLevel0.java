package com.daitangroup.controllers;

import com.daitangroup.ResponseContent;
import com.daitangroup.User;
import com.daitangroup.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.POST;


@RestController
public class MessengerBackendControllerLevel0 {

    @Autowired
    @Qualifier("UserDaoMysqlImpl")
    private UserDao userDao;

    private static final String C_CREATE_SERVICE = "create";
    private static final String C_READ_SERVICE = "read";
    private static final String C_UPDATE_SERVICE = "update";
    private static final String C_DELETE_SERVICE = "delete";

    @RequestMapping(value="/messenger/userService", method=POST)
    @ResponseBody
    public ResponseContent doUserService(@RequestParam(name="service", defaultValue="create") String service,
                                         @RequestParam(name="id", required=false) Integer id,
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
                    userDao.create(user);
                    responseContent.setService(C_CREATE_SERVICE);
                    users.add(user);
                    responseContent.setUsers(users);
                    break;
                case C_READ_SERVICE:
                    if (id != null) {
                        User gotUser = userDao.getById(id);
                        users.add(gotUser);
                        responseContent.setUsers(users);
                    } else {
                        List gotUsers = userDao.getAll();
                        responseContent.setUsers(gotUsers);
                    }
                    responseContent.setService(C_READ_SERVICE);
                    break;
                case C_UPDATE_SERVICE:
                    user.setId(id);
                    userDao.update(user);
                    responseContent.setService(C_UPDATE_SERVICE);
                    users.add(user);
                    responseContent.setUsers(users);
                    break;
                case C_DELETE_SERVICE:
                    user.setId(id);
                    userDao.delete(user);
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
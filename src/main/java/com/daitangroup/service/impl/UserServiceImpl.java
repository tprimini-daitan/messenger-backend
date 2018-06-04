package com.daitangroup.service.impl;

import com.daitangroup.ResponseContent;
import com.daitangroup.entity.User;
import com.daitangroup.repo.UserRepository;
import com.daitangroup.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    public User insert(String name, String password) {
        User user = new User();

        user.setName(name);
        user.setPassword(password);

        try {
            return userRepository.insert(user);
        } catch (Exception e) {
            return null;
        }
    }
}

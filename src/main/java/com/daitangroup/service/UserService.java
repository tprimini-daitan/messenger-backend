package com.daitangroup.service;

import com.daitangroup.entity.User;

import java.util.List;

public interface UserService {
    User insert(User newUser);
    User findById(String id);
    List<User> findByName(String name);
    List<User> findAll();
    User update(User userToBeUpdated);
    boolean delete(String id);
}

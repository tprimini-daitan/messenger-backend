package com.daitangroup.dao;

import com.daitangroup.User;

import java.util.List;

public interface UserDao {
    void create(User user);
    User getByName(String name);
    User getById(Integer id);
    List getAll();
    void delete(User user);
    void update(User user);
}

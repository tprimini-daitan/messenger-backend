package com.daitangroup.service;

import com.daitangroup.entity.User;

public interface UserService {
    User insert(String name, String password);
}

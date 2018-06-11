package com.daitangroup.service.impl;

import com.daitangroup.entity.User;
import com.daitangroup.repo.UserRepository;
import com.daitangroup.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User insert(User newUser) {
        if (newUser == null) {
            return null;
        }

        try {
            return userRepository.insert(newUser);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public User findById(String id) {
        User user = null;
        try {
            if (id != null) {
                Optional<User> gotUser = userRepository.findById(id);
                if (gotUser.isPresent()) {
                    user = gotUser.get();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return user;
    }

    public List<User> findByName(String name) {
        List<User> gotUsers = null;
        try {
            if (name != null) {
                gotUsers = userRepository.findByName(name);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return gotUsers;
    }

    public List<User> findAll() {
        List<User> gotUsers = null;
        try {
            gotUsers = userRepository.findAll();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return gotUsers;
    }

    public User update(User userToBeUpdated) {
        if (userToBeUpdated == null) {
            return null;
        }

        try {
            return userRepository.save(userToBeUpdated);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean delete(String id) {
        User userToBeRemoved = findById(id);

        if (userToBeRemoved == null) {
            return false;
        }

        try {
            userRepository.delete(userToBeRemoved);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}

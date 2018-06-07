package com.daitangroup.service;

import com.daitangroup.entity.Group;

import java.util.List;

public interface GroupService {
    Group insert(Group newGroup);
    Group findById(String id);
    List<Group> findAll();
}

package com.daitangroup.service.impl;

import com.daitangroup.entity.Group;
import com.daitangroup.repo.GroupRepository;
import com.daitangroup.service.GroupService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GroupServiceImpl implements GroupService {
    private final GroupRepository groupRepository;

    public GroupServiceImpl(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    public Group insert(Group newGroup) {
        if (newGroup == null) {
            return null;
        }

        try {
            return groupRepository.insert(newGroup);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Group findById(String id) {
        Group group = null;
        try {
            if (id != null) {
                Optional<Group> gotGroup = groupRepository.findById(id);
                if (gotGroup.isPresent()) {
                    group = gotGroup.get();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return group;
    }

    public List<Group> findAll() {
        List<Group> gotGroups = null;
        try {
            gotGroups = groupRepository.findAll();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return gotGroups;
    }
}

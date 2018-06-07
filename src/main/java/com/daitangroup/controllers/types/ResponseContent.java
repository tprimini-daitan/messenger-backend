package com.daitangroup.controllers.types;

import com.daitangroup.entity.Group;
import com.daitangroup.entity.User;
import org.springframework.hateoas.ResourceSupport;

import java.util.List;

public class ResponseContent extends ResourceSupport {
    private String service;
    private List<User> users;
    private List<Group> groups;

    public void setService(String service) {
        this.service = service;
    }

    public String getService() {
        return this.service;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<User> getUsers() {
        return this.users;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }
}

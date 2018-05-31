package com.daitangroup;

import java.util.List;

public class ResponseContent {
    private String service;
    private List<User> users;

    public ResponseContent() {

    }

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
}

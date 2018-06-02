package com.daitangroup.entity;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@Document(collection="group")
public class Group {

    @Id
    private String id;

    private String name;

    private Set<String> participants;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addParticipant(String participant) {
        participants.add(participant);
    }

    public void removeParticipant(String participant) {
        participants.remove(participant);
    }

    public Set<String> getParticipants() {
        return participants;
    }

    public void setParticipants(Set<String> participants) {
        this.participants = participants;
    }
}

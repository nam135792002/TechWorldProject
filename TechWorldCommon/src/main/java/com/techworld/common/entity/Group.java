package com.techworld.common.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user_groups")
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String groupNames;

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GroupMember> listGroups = new ArrayList<>();

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GroupMessage> listMessages = new ArrayList<>();
    public Group() {

    }

    public Group(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGroupName() {
        return groupNames;
    }

    public void setGroupName(String groupName) {
        this.groupNames = groupName;
    }

    public List<GroupMember> getListGroups() {
        return listGroups;
    }

    public void setListGroups(List<GroupMember> listGroups) {
        this.listGroups = listGroups;
    }

    public List<GroupMessage> getListMessages() {
        return listMessages;
    }

    public void setListMessages(List<GroupMessage> listMessages) {
        this.listMessages = listMessages;
    }
}

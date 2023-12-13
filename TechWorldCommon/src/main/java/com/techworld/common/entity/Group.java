package com.techworld.common.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "user_groups")
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String groupNames;

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
}

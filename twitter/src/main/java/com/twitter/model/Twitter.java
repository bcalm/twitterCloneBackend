package com.twitter.model;

import javax.persistence.*;

@Entity
@Table(name = "twitter")
public class Twitter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column(name = "userId")
    private String userId;

    @Column(name = "userName")
    private String userName;

    @Column(name = "joinedAt")
    private String joinedAT;

    @Column(name = "followersCount")
    private int followersCount;

    @Column(name = "followingCount")
    private int followingCount;

    public Twitter() {
        super();
    }

    public Twitter(String userId, String userName, String joinedAT, int followersCount, int followingCount) {
        this.userId = userId;
        this.userName = userName;
        this.joinedAT = joinedAT;
        this.followersCount = followersCount;
        this.followingCount = followingCount;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getJoinedAT() {
        return joinedAT;
    }

    public void setJoinedAT(String joinedAT) {
        this.joinedAT = joinedAT;
    }

    public int getFollowersCount() {
        return followersCount;
    }

    public void setFollowersCount(int followersCount) {
        this.followersCount = followersCount;
    }

    public int getFollowingCount() {
        return followingCount;
    }

    public void setFollowingCount(int followingCount) {
        this.followingCount = followingCount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}


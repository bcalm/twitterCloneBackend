package com.twitter.model;
import javax.persistence.*;

@Entity
@Table(name = "twitter")
public class Twitter {

    @Id
    private String id;

    @Column(name = "userName")
    private String userName;

    @Column(name = "joinedAt")
    private String joinedAT;

    @Column
    private String imageUrl;

    @Column
    private String dob;

    @Column
    private String bio;

    @Column(name = "followersCount")
    private int followersCount;

    @Column(name = "followingCount")
    private int followingCount;

    public Twitter() {
        super();
    }

    public Twitter(String id, String userName, String joinedAT, String imageUrl, String dob, String bio, int followersCount, int followingCount) {
        this.id = id;
        this.userName = userName;
        this.joinedAT = joinedAT;
        this.imageUrl = imageUrl;
        this.dob = dob;
        this.bio = bio;
        this.followersCount = followersCount;
        this.followingCount = followingCount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
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
}


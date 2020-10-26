package com.twitter.model;

import javax.persistence.*;

@Entity
@Table(name = "employees")
public class Tweet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "content")
    private String content;

    @Column(name = "userName")
    private String userName;

    @Column(name = "userId")
    private String userId;


    @Column(name = "timeStamp")
    private String timeStamp;

    public Tweet() {
        super();
    }

    public Tweet(String content, String userName, String userId, String timeStamp) {
        this.content = content;
        this.userName = userName;
        this.userId = userId;
        this.timeStamp = timeStamp;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }


    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }
}


package com.twitter.model;

import javax.persistence.*;

@Entity
@Table(name = "employees")
public class Tweet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;


    @Column(name = "type")
    private String type;

    @Column(name = "userId")
    private String userId;


    @Column(name = "content")
    private String content;

    @Column(name = "timeStamp")
    private String timeStamp;

    @Column(name = "reference")
    private long reference;

    @Column(name = "likeCount")
    private long likeCount;

    @Column(name = "replyCount")
    private long replyCount;

    @Column(name = "retweetCount")
    private long retweetCount;


    public Tweet() {
        super();
    }

    public Tweet(String type, String userId, String content, String timeStamp, long reference, long likeCount, long replyCount, long retweetCount) {
        this.type = type;
        this.userId = userId;
        this.content = content;
        this.timeStamp = timeStamp;
        this.reference = reference;
        this.likeCount = likeCount;
        this.replyCount = replyCount;
        this.retweetCount = retweetCount;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public long getReference() {
        return reference;
    }

    public void setReference(long reference) {
        this.reference = reference;
    }

    public long getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(long likeCount) {
        this.likeCount = likeCount;
    }

    public long getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(long replyCount) {
        this.replyCount = replyCount;
    }

    public long getRetweetCount() {
        return retweetCount;
    }

    public void setRetweetCount(long retweetCount) {
        this.retweetCount = retweetCount;
    }
}


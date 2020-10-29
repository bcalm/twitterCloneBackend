package com.twitter.model;

import javax.persistence.*;

@Entity
@Table(name = "like_tweets")
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "tweetId")
    private long tweetId;

    @Column(name = "userId")
    private String userId;

    public Like(long tweetId, String userId) {
        this.tweetId = tweetId;
        this.userId = userId;
    }


    public Like() {
        super();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getTweetId() {
        return tweetId;
    }

    public void setTweetId(long tweetId) {
        this.tweetId = tweetId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}

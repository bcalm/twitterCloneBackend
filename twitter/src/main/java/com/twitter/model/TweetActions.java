package com.twitter.model;

import javax.persistence.*;

@Entity
@Table(name = "user_tweet_actions")
public class TweetActions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long tweetId;

    @Column(name = "isLiked")
    private boolean liked;

    @Column(name = "isRetweeted")
    private boolean retweeted;

    @Column(name = "isReplied")
    private boolean replied;

    @Column(name = "likeCount")
    private int likeCount;

    @Column(name = "replyCount")
    private int replyCount;

    @Column(name = "retweetCount")
    private int retweetCount;


    public TweetActions(boolean liked, boolean retweeted, boolean replied, int likeCount, int replyCount, int retweetCount) {
        this.liked = liked;
        this.retweeted = retweeted;
        this.replied = replied;
        this.likeCount = likeCount;
        this.replyCount = replyCount;
        this.retweetCount = retweetCount;
    }


    public TweetActions() {
        super();
    }

    public long getTweetId() {
        return tweetId;
    }

    public void setTweetId(long tweetId) {
        this.tweetId = tweetId;
    }

    public boolean isLiked() {
        return liked;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }

    public boolean isRetweeted() {
        return retweeted;
    }

    public void setRetweeted(boolean retweeted) {
        this.retweeted = retweeted;
    }

    public boolean isReplied() {
        return replied;
    }

    public void setReplied(boolean replied) {
        this.replied = replied;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public int getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(int replyCount) {
        this.replyCount = replyCount;
    }

    public int getRetweetCount() {
        return retweetCount;
    }

    public void setRetweetCount(int retweetCount) {
        this.retweetCount = retweetCount;
    }

}

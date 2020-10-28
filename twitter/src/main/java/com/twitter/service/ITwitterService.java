package com.twitter.service;

import com.twitter.model.Tweet;
import com.twitter.model.Twitter;
import com.twitter.model.TweetActions;

import java.util.List;

public interface ITwitterService {
    List<Tweet> getRetweets();

    List<Tweet> getLikeTweets();

    List<Tweet> addTweet(String userId, Tweet tweet);

    List<Tweet> getTweets(String userId);

    List<Tweet> deleteTweet(Long id);

    TweetActions toggleLike(Long id);

    TweetActions getUserActionDetails(Long id);

    List<Tweet> addRetweet(Tweet tweet, Long id);

    List<Tweet> deleteRetweet(Long id);

    Twitter getTwitterDetails(String userId);

    Twitter saveUserDetails(Twitter twitter);
}

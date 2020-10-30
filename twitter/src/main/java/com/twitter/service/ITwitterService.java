package com.twitter.service;

import com.twitter.model.Tweet;
import com.twitter.model.Twitter;

import java.util.List;

public interface ITwitterService {

    Twitter saveUserDetails(Twitter twitter);

    Twitter getTwitterDetails(String userId);

    List<Tweet> addTweet(String userId, Tweet tweet);

    List<Tweet> getTweets(String userId);

    Tweet toggleLike(String userId, Long id);

    List<Tweet> deleteTweet(String userId, Long id);

    List<Tweet> getLikeTweets(String userId);
}

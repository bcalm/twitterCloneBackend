package com.twitter.controller;

import com.twitter.model.Tweet;
import com.twitter.model.Twitter;
import com.twitter.service.TwitterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {"*"})
@RestController
public class TwitterController {

    private final TwitterService twitterService;

    @Autowired
    public TwitterController(TwitterService twitterService) {
        this.twitterService = twitterService;
    }

    @PostMapping("/api/saveUserDetails")
    public Twitter saveUserDetails(@RequestBody Twitter twitter) {
        return this.twitterService.saveUserDetails(twitter);
    }

    @GetMapping("/api/getUserDetails/{userId}")
    public Twitter getTwitterDetails(@PathVariable String userId) {
        return this.twitterService.getTwitterDetails(userId);
    }

    @PostMapping("/api/addTweet")
    public List<Tweet> addTweet(@RequestHeader(value = "userId") String userId,@RequestBody Tweet tweet) {
        return this.twitterService.addTweet(userId, tweet);
    }

    @GetMapping("/api/getTweets")
    public List<Tweet> getAllTweets(@RequestHeader(value = "userId") String userId) {
        return this.twitterService.getTweets(userId);
    }

    @PostMapping("/api/addLike")
    public Tweet toggleLike(@RequestHeader(value = "userId") String userId, @RequestBody Long tweetId) {
        return this.twitterService.toggleLike(userId, tweetId);
    }

//    @PostMapping("/api/getUserActionDetails")
//    public TweetActions getUserActionDetails(@RequestBody Long tweetId) {
//        return this.twitterService.getUserActionDetails(tweetId);
//    }
//
//    @PostMapping("/api/addRetweet/{tweetId}")
//    public List<Tweet> addRetweet(@RequestBody Tweet tweet, @PathVariable Long tweetId) {
//        return this.twitterService.addRetweet(tweet, tweetId);
//    }
//
//    @GetMapping("/api/getRetweets")
//    public List<Tweet> getRetweets() {
//        return this.twitterService.getRetweets();
//    }
//
//
//    @GetMapping("/api/getLikeTweets")
//    public List<Tweet> getLikeTweets() {
//        return this.twitterService.getLikeTweets();
//    }
//
//
//    @PostMapping("/api/deleteTweet")
//    public List<Tweet> deleteTweet(@RequestBody Long tweetId) {
//        return this.twitterService.deleteTweet(tweetId);
//    }
//
//    @PostMapping("/api/deleteRetweet")
//    public List<Tweet> deleteRetweet(@RequestBody Long tweetId) {
//        return this.twitterService.deleteRetweet(tweetId);
//    }
}


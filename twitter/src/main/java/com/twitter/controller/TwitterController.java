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
    public List<Tweet> addTweet(@RequestHeader(value = "userId") String userId, @RequestBody Tweet tweet) {
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

    @PostMapping("/api/deleteTweet")
    public List<Tweet> deleteTweet(@RequestHeader(value = "userId") String userId, @RequestBody Long tweetId) {
        return this.twitterService.deleteTweet(userId, tweetId);
    }

    @GetMapping("/api/getLikeTweets")
    public List<Tweet> getLikeTweets(@RequestHeader(value = "userId") String userId) {
        return this.twitterService.getLikeTweets(userId);
    }
}


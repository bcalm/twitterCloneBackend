package com.twitter.service;

import com.twitter.model.Tweet;
import com.twitter.model.Twitter;
import com.twitter.model.Like;
import com.twitter.repository.TweetRepository;
import com.twitter.repository.LikeRepository;
import com.twitter.repository.TwitterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TwitterService implements ITwitterService {

    private final TweetRepository tweetRepository;
    private final LikeRepository likeRepository;
    private final TwitterRepository twitterRepository;

    @Autowired
    public TwitterService(TweetRepository tweetRepository, LikeRepository likeRepository, TwitterRepository twitterRepository) {
        this.tweetRepository = tweetRepository;
        this.likeRepository = likeRepository;
        this.twitterRepository = twitterRepository;
    }

    public Twitter saveUserDetails(Twitter twitter) {
        return this.twitterRepository.save(twitter);
    }


    @Override
    public Twitter getTwitterDetails(String userId) {
        Optional<Twitter> twitter = this.twitterRepository.findById(userId);
        if (twitter.isEmpty()) {
            return null;
        }
        return twitter.get();
    }

    @Override
    public List<Tweet> addTweet(String userId, Tweet tweet) {
        tweet.setUserId(userId);
        this.tweetRepository.save(tweet);
        return this.tweetRepository.findAll();
    }

    @Override
    public List<Tweet> getTweets(String userId) {
        return this.tweetRepository.findAllByUserId(userId);
    }

    @Override
    public Tweet toggleLike(String userId, Long id) {
        Optional<Tweet> tweetDetails = this.tweetRepository.findById(id);
        if (tweetDetails.isEmpty()) return null;
        Tweet tweet = tweetDetails.get();

        List<Like> likeHistory = this.likeRepository.findAllByTweetId(id);
        List<Like> userLiked = likeHistory.stream().filter(t -> t.getUserId().equals(userId)).collect(Collectors.toList());

        long addValue = tweet.getLikeCount();
        if (likeHistory.isEmpty() || userLiked.size() == 0) {
            this.likeRepository.save(new Like(id, userId));
            addValue += 1;
        } else {
            this.likeRepository.deleteByTweetIdAndUserId(id, userId);
            addValue -= 1;
        }
        tweet.setLikeCount(addValue);
        return this.tweetRepository.save(tweet);
    }

    @Override
    public List<Tweet> deleteTweet(String userId, Long id) {
        Optional<Tweet> tweetDetails = this.tweetRepository.findById(id);
        tweetDetails.ifPresent(tweet -> {
            if (tweet.getUserId().equals(userId)) {
                this.tweetRepository.deleteById(id);
            }
        });
        return this.tweetRepository.findAll();
    }


    @Override
    public List<Tweet> getLikeTweets(String userId) {
        List<Like> userLikedTweets = this.likeRepository.findAllByUserId(userId);
        return userLikedTweets.stream().collect(ArrayList::new, (ArrayList<Tweet> tweets, Like userLikedTweet) -> {
            Optional<Tweet> tweet = this.tweetRepository.findById(userLikedTweet.getId());
            tweet.ifPresent(tweets::add);
        }, ArrayList::addAll);
    }
}

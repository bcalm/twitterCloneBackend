package com.twitter.service;

import com.twitter.model.Tweet;
import com.twitter.model.Twitter;
import com.twitter.model.TweetActions;
import com.twitter.repository.TweetRepository;
import com.twitter.repository.TweetActionRepository;
import com.twitter.repository.TwitterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TwitterService implements ITwitterService {

    private final TweetRepository tweetRepository;
    private final TweetActionRepository tweetActionRepository;
    private final TwitterRepository twitterRepository;

    @Autowired
    public TwitterService(TweetRepository tweetRepository, TweetActionRepository tweetActionRepository, TwitterRepository twitterRepository) {
        this.tweetRepository = tweetRepository;
        this.tweetActionRepository = tweetActionRepository;
        this.twitterRepository = twitterRepository;
    }

    @Override
    public List<Tweet> getTweets() {
        return this.tweetRepository.findAll();
    }

    @Override
    public List<Tweet> getRetweets() {
        List<Tweet> retweets = new ArrayList<>();
        List<TweetActions> allTweetsActions = this.tweetActionRepository.findAll();
        for (TweetActions tweetActions : allTweetsActions) {
            if (tweetActions.isRetweeted()) {
                Optional<Tweet> tweet = this.tweetRepository.findById(tweetActions.getTweetId());
                tweet.ifPresent(retweets::add);
            }
        }
        return retweets;
    }

    @Override
    public List<Tweet> getLikeTweets() {
        List<Tweet> likeTweets = new ArrayList<>();
        List<TweetActions> allTweetsActions = this.tweetActionRepository.findAll();
        for (TweetActions tweetActions : allTweetsActions) {
            if (tweetActions.isLiked()) {
                Optional<Tweet> tweet = this.tweetRepository.findById(tweetActions.getTweetId());
                tweet.ifPresent(likeTweets::add);
            }
        }
        return likeTweets;
    }

    @Override
    public List<Tweet> addTweet(Tweet tweet) {
        this.tweetRepository.save(tweet);
        this.tweetActionRepository.save(new TweetActions(false, false, false, 0, 0, 0));
        return this.tweetRepository.findAll();
    }

    @Override
    public List<Tweet> deleteTweet(Long id) {
        this.tweetRepository.deleteById(id);
        this.tweetActionRepository.deleteById(id);
        return this.tweetRepository.findAll();
    }

    @Override
    public TweetActions toggleLike(Long id) {
        Optional<TweetActions> tweetActionsDetails = this.tweetActionRepository.findById(id);
        if (tweetActionsDetails.isEmpty()) return null;
        TweetActions tweetActions = tweetActionsDetails.get();
        int value = tweetActions.isLiked() ? -1 : 1;
        int addValue = (tweetActions.getLikeCount() + value);
        tweetActions.setLikeCount(addValue);
        tweetActions.setLiked(!tweetActions.isLiked());
        this.tweetActionRepository.save(tweetActions);
        return tweetActions;
    }

    @Override
    public TweetActions getUserActionDetails(Long id) {
        Optional<TweetActions> tweetAction = this.tweetActionRepository.findById(id);
        if (tweetAction.isEmpty()) {
            return null;
        }
        return tweetAction.get();
    }

    @Override
    public List<Tweet> addRetweet(Tweet tweet, Long id) {
        Optional<TweetActions> tweetActionDetails = this.tweetActionRepository.findById(id);
        tweetActionDetails.ifPresent(tweetActions -> {
            tweetActions.setRetweetCount(tweetActions.getRetweetCount() + 1);
            tweetActions.setRetweeted(true);
            this.tweetActionRepository.save(tweetActions);
        });
        return this.addTweet(tweet);
    }

    @Override
    public List<Tweet> deleteRetweet(Long id) {
        Optional<TweetActions> tweetDetails = this.tweetActionRepository.findById(id);
        tweetDetails.ifPresent(tweetActions -> {
                    tweetActions.setRetweetCount(tweetActions.getRetweetCount() - 1);
                    tweetActions.setRetweeted(false);
                    this.tweetActionRepository.save(tweetActions);
                }
        );
        return this.tweetRepository.findAll();
    }

    @Override
    public Twitter getTwitterDetails(String userId) {
        return this.twitterRepository.findAll().get(0);
    }

    @Override
    public Twitter saveUserDetails(Twitter twitter) {
        return this.twitterRepository.save(twitter);
    }
}

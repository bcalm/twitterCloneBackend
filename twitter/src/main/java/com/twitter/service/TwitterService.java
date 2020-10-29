package com.twitter.service;

import com.twitter.model.Tweet;
import com.twitter.model.Twitter;
import com.twitter.model.Like;
import com.twitter.repository.TweetRepository;
import com.twitter.repository.LikeRepository;
import com.twitter.repository.TwitterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
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


    @Override
    public List<Tweet> getRetweets() {
        return null;
    }

    @Override
    public List<Tweet> getLikeTweets() {
        return null;
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
    public List<Tweet> deleteTweet(Long id) {
        return null;
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
        this.tweetRepository.save(tweet);
        return tweet;
    }

    @Override
    public Like getUserActionDetails(Long id) {
        return null;
    }

    @Override
    public List<Tweet> addRetweet(Tweet tweet, Long id) {
        return null;
    }

    @Override
    public List<Tweet> deleteRetweet(Long id) {
        return null;
    }

    @Override
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
//
//    @Override
//    public List<Tweet> getTweets() {
//        return this.tweetRepository.findAll();
//    }
//
//    @Override
//    public List<Tweet> getRetweets() {
//        Stream<TweetActions> tweetActionsStream = this.tweetActionRepository.findAll().stream().filter(TweetActions::isRetweeted);
//        return getFilteredTweets(tweetActionsStream);
//    }
//
//    private ArrayList<Tweet> getFilteredTweets(Stream<TweetActions> tweetActionsStream) {
//        return tweetActionsStream.collect(ArrayList::new,
//                (ArrayList<Tweet> tweets, TweetActions tweetAction) -> {
//                    Optional<Tweet> tweet = this.tweetRepository.findById(tweetAction.getTweetId());
//                    tweet.ifPresent(tweets::add);
//                },
//                ArrayList::addAll
//        );
//    }
//
//    @Override
//    public List<Tweet> getLikeTweets() {
//        Stream<TweetActions> tweetActionsStream = this.tweetActionRepository.findAll().stream().filter(TweetActions::isLiked);
//        return getFilteredTweets(tweetActionsStream);
//    }
//
//    @Override
//    public List<Tweet> addTweet(Tweet tweet) {
//        this.tweetRepository.save(tweet);
//        this.tweetActionRepository.save(new TweetActions(false, false, false, 0, 0, 0));
//        return this.tweetRepository.findAll();
//    }
//
//    @Override
//    public List<Tweet> deleteTweet(Long id) {
//        this.tweetRepository.deleteById(id);
//        this.tweetActionRepository.deleteById(id);
//        return this.tweetRepository.findAll();
//    }
//
//    @Override
//    public TweetActions toggleLike(Long id) {
//        Optional<TweetActions> tweetActionsDetails = this.tweetActionRepository.findById(id);
//        if (tweetActionsDetails.isEmpty()) return null;
//        TweetActions tweetActions = tweetActionsDetails.get();
//        int value = tweetActions.isLiked() ? -1 : 1;
//        int addValue = (tweetActions.getLikeCount() + value);
//        tweetActions.setLikeCount(addValue);
//        tweetActions.setLiked(!tweetActions.isLiked());
//        this.tweetActionRepository.save(tweetActions);
//        return tweetActions;
//    }
//
//    @Override
//    public TweetActions getUserActionDetails(Long id) {
//        Optional<TweetActions> tweetAction = this.tweetActionRepository.findById(id);
//        if (tweetAction.isEmpty()) {
//            return null;
//        }
//        return tweetAction.get();
//    }
//
//    @Override
//    public List<Tweet> addRetweet(Tweet tweet, Long id) {
//        Optional<TweetActions> tweetActionDetails = this.tweetActionRepository.findById(id);
//        tweetActionDetails.ifPresent(tweetActions -> {
//            tweetActions.setRetweetCount(tweetActions.getRetweetCount() + 1);
//            tweetActions.setRetweeted(true);
//            this.tweetActionRepository.save(tweetActions);
//        });
//        return this.addTweet(tweet);
//    }
//
//    @Override
//    public List<Tweet> deleteRetweet(Long id) {
//        Optional<TweetActions> tweetDetails = this.tweetActionRepository.findById(id);
//        tweetDetails.ifPresent(tweetActions -> {
//                    tweetActions.setRetweetCount(tweetActions.getRetweetCount() - 1);
//                    tweetActions.setRetweeted(false);
//                    this.tweetActionRepository.save(tweetActions);
//                }
//        );
//        return this.tweetRepository.findAll();
//    }

}

package com.twitter.service;

import com.twitter.model.Tweet;
import com.twitter.model.TweetActions;
import com.twitter.model.Twitter;
import com.twitter.repository.TweetActionRepository;
import com.twitter.repository.TweetRepository;
import com.twitter.repository.TwitterRepository;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TwitterServiceTest {

    @InjectMocks
    private TwitterService twitterService;
    @Mock
    private TweetRepository tweetRepository;
    @Mock
    private TweetActionRepository tweetActionRepository;
    @Mock
    private TwitterRepository twitterRepository;

    @Test
    public void shouldGetAllTheTweets() {
        Tweet tweet = new Tweet("testing", "Vicky", "bcalm", "20-10-2020");
        List<Tweet> tweets = Collections.singletonList(tweet);

        Mockito.when(this.tweetRepository.findAll()).thenReturn(tweets);
        assertEquals(twitterService.getTweets(), tweets);
    }

    @Test
    public void shouldGetAllTheRetweets() {
        Tweet tweet1 = new Tweet("testing1", "Vicky", "bcalm", "20-10-2020");
        Tweet tweet2 = new Tweet("testing2", "Vicky", "bcalm", "20-10-2020");

        TweetActions tweetActions1 = new TweetActions(false, false, false, 0, 0, 0);
        TweetActions tweetActions2 = new TweetActions(false, true, false, 0, 0, 1);
        List<TweetActions> tweetsActions = Arrays.asList(tweetActions1, tweetActions2);

        Mockito.when(this.tweetActionRepository.findAll()).thenReturn(tweetsActions);
        Mockito.when(this.tweetRepository.findById(tweet1.getId())).thenReturn(java.util.Optional.of(tweet1));
        Mockito.when(this.tweetRepository.findById(tweet2.getId())).thenReturn(java.util.Optional.of(tweet2));

        Assertions.assertThat(this.twitterService.getRetweets()).containsExactly(tweet2);
    }

    @Test
    public void getLikeTweets() {
        Tweet tweet1 = new Tweet("testing1", "Vicky", "bcalm", "20-10-2020");
        Tweet tweet2 = new Tweet("testing2", "Vicky", "bcalm", "20-10-2020");

        TweetActions tweetActions1 = new TweetActions(false, false, false, 0, 0, 0);
        TweetActions tweetActions2 = new TweetActions(true, false, false, 1, 0, 0);
        List<TweetActions> tweetsActions = Arrays.asList(tweetActions1, tweetActions2);

        Mockito.when(this.tweetActionRepository.findAll()).thenReturn(tweetsActions);
        Mockito.when(this.tweetRepository.findById(tweet1.getId())).thenReturn(java.util.Optional.of(tweet1));
        Mockito.when(this.tweetRepository.findById(tweet2.getId())).thenReturn(java.util.Optional.of(tweet2));

        Assertions.assertThat(this.twitterService.getLikeTweets()).containsExactly(tweet2);
    }

    @Test
    public void shouldAddTheTweet() {
        Tweet tweet = new Tweet("testing", "bcalm", "Vikram", "20-10-2020");
        TweetActions tweetActions = new TweetActions(false, false, false, 0, 0, 0);
        Twitter twitter = new Twitter("bcalm", "vikram", "20-10-2020", 0, 0);

        Mockito.when(this.tweetRepository.save(tweet)).thenReturn(tweet);
        Mockito.when(this.tweetActionRepository.save(tweetActions)).thenReturn(tweetActions);
        Mockito.when(this.twitterRepository.findAll()).thenReturn(Collections.singletonList(twitter));
        Mockito.when(this.tweetRepository.findAll()).thenReturn(Collections.singletonList(tweet));

        Assertions.assertThat(this.twitterService.addTweet(tweet)).containsExactly(tweet);
    }

    @Test
    public void shouldDeleteTheTweet() {
        Mockito.when(this.tweetRepository.findAll()).thenReturn(null);

        assertNull(this.twitterService.deleteTweet(1L));
        Mockito.verify(this.tweetActionRepository).deleteById(1L);
        Mockito.verify(this.tweetRepository).deleteById(1L);
    }

    @Test
    public void shouldLikeTheTweet() {
        TweetActions tweetActions = new TweetActions(false, false, false, 0, 0, 0);

        Mockito.when(this.tweetActionRepository.findById(tweetActions.getTweetId())).thenReturn(java.util.Optional.of(tweetActions));
        Mockito.when(this.tweetActionRepository.save(tweetActions)).thenReturn(tweetActions);

        assertEquals(tweetActions, this.twitterService.toggleLike(tweetActions.getTweetId()));
        assertEquals(tweetActions.getLikeCount(), 1);
        assertTrue(tweetActions.isLiked());
    }

    @Test
    public void shouldUnlikeTheTweet() {
        TweetActions tweetActions = new TweetActions(true, false, false, 1, 0, 0);

        Mockito.when(this.tweetActionRepository.findById(tweetActions.getTweetId())).thenReturn(java.util.Optional.of(tweetActions));
        Mockito.when(this.tweetActionRepository.save(tweetActions)).thenReturn(tweetActions);

        assertEquals(tweetActions, this.twitterService.toggleLike(tweetActions.getTweetId()));
        assertEquals(tweetActions.getLikeCount(), 0);
        assertFalse(tweetActions.isLiked());
    }

    @Test
    public void shouldGiveUserActionOnSpecificTweet() {
        Mockito.when(this.tweetActionRepository.findById(1L)).thenReturn(Optional.empty());

        assertNull(this.twitterService.getUserActionDetails(1L));

        TweetActions tweetActions = new TweetActions(false, false, false, 0, 0, 0);
        Mockito.when(this.tweetActionRepository.findById(tweetActions.getTweetId())).thenReturn(Optional.of(tweetActions));

        assertEquals(this.twitterService.getUserActionDetails(tweetActions.getTweetId()), tweetActions);
    }

    @Test
    public void shouldAddTheRetweet() {
        Tweet tweet = new Tweet("testing", "bcalm", "vikram", "20-10-2020");
        TweetActions tweetActions = new TweetActions(false, false, false, 0, 0, 0);
        Twitter twitter = new Twitter("bcalm", "vikram", "20-10-2020", 0, 0);

        Mockito.when(this.twitterRepository.findAll()).thenReturn(Collections.singletonList(twitter));
        Mockito.when(this.tweetActionRepository.findById(tweetActions.getTweetId())).thenReturn(Optional.of(tweetActions));
        Mockito.when(this.tweetRepository.findById(tweet.getId())).thenReturn(Optional.of(tweet));
        Mockito.when(this.tweetRepository.findAll()).thenReturn(Collections.singletonList(tweet));

        Assertions.assertThat(this.twitterService.addRetweet(tweet, tweet.getId())).containsExactly(tweet);
    }

    @Test
    public void shouldDeleteTheRetweet() {
        TweetActions tweetActions = new TweetActions(false, true, false, 0, 0, 1);

        Mockito.when(this.tweetActionRepository.findById(tweetActions.getTweetId())).thenReturn(Optional.of(tweetActions));
        Mockito.when(this.tweetRepository.findAll()).thenReturn(null);

        assertNull(this.twitterService.deleteRetweet(tweetActions.getTweetId()));
        assertFalse(tweetActions.isRetweeted());
        assertEquals(tweetActions.getRetweetCount(), 0);
    }

    @Test
    public void shouldGetTwitterDetails() {
        Twitter twitter = new Twitter("bcalm", "vikram", "20-10-2020", 0, 0);
        Mockito.when(this.twitterRepository.findAll()).thenReturn(Collections.singletonList(twitter));

        assertEquals(this.twitterService.getTwitterDetails("bcalm"), twitter);
    }
}
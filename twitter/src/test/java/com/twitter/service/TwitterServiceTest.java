package com.twitter.service;

import com.twitter.model.Like;
import com.twitter.model.Tweet;
import com.twitter.model.Twitter;
import com.twitter.repository.LikeRepository;
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

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TwitterServiceTest {

    @InjectMocks
    private TwitterService twitterService;
    @Mock
    private TweetRepository tweetRepository;
    @Mock
    private LikeRepository likeRepository;
    @Mock
    private TwitterRepository twitterRepository;

    @Test
    public void shouldSaveTheUserDetails() {
        Twitter twitter = new Twitter("id", "name", "joinedAt", "url", "dob", "bio", 0, 0);

        Mockito.when(this.twitterRepository.save(any(Twitter.class))).thenReturn(twitter);

        Assertions.assertThat(this.twitterService.saveUserDetails(twitter)).isEqualTo(twitter);
    }

    @Test
    public void shouldGetTwitterDetails() {
        Twitter twitter = new Twitter("id", "name", "joinedAt", "url", "dob", "bio", 0, 0);
        Mockito.when(this.twitterRepository.findById("bcalm")).thenReturn(java.util.Optional.of(twitter));

        assertEquals(this.twitterService.getTwitterDetails("bcalm"), twitter);
    }


    @Test
    public void shouldAddTheTweet() {
        Tweet tweet = new Tweet("Tweet", "bcalm", "testing", "20-10-2020", 0, 0, 0, 0);

        Mockito.when(this.tweetRepository.save(tweet)).thenReturn(tweet);
        Mockito.when(this.tweetRepository.findAll()).thenReturn(Collections.singletonList(tweet));

        Assertions.assertThat(this.twitterService.addTweet("bcalm", tweet)).containsExactly(tweet);
    }


    @Test
    public void shouldGetAllTheTweets() {
        Tweet tweet = new Tweet("Tweet", "bcalm", "testing", "20-10-2020", 0, 0, 0, 0);

        Mockito.when(this.tweetRepository.findAllByUserId("bcalm")).thenReturn(Collections.singletonList(tweet));

        Assertions.assertThat(this.twitterService.getTweets("bcalm")).containsExactly(tweet);
    }


    @Test
    public void shouldLikeTheTweet() {
        Tweet tweet = new Tweet("Tweet", "bcalm", "testing", "20-10-2020", 0, 0, 0, 0);
        Like likeTweet = new Like(1, "bcalm");

        Mockito.when(this.tweetRepository.findById(1L)).thenReturn(java.util.Optional.of(tweet));
        Mockito.when(this.likeRepository.findAllByTweetId(1L)).thenReturn(Collections.singletonList(likeTweet));
        Mockito.when(this.likeRepository.save(any())).thenReturn(likeTweet);
        Mockito.when(this.tweetRepository.save(any(Tweet.class))).thenReturn(tweet);

        Assertions.assertThat(this.twitterService.toggleLike("bcalm", 1L)).isEqualTo(tweet);
    }


    @Test
    public void getLikeTweets() {
        Tweet tweet = new Tweet("Tweet", "bcalm", "testing", "20-10-2020", 0, 0, 0, 0);
        Like likeTweet = new Like(1, "bcalm");

        Mockito.when(this.likeRepository.findAllByUserId("bcalm")).thenReturn(Collections.singletonList(likeTweet));
        Mockito.when(this.tweetRepository.findById(anyLong())).thenReturn(java.util.Optional.of(tweet));

        Assertions.assertThat(this.twitterService.getLikeTweets("bcalm")).containsExactly(tweet);
    }

    @Test
    public void shouldDeleteTheTweet() {
        Tweet tweet = new Tweet("Tweet", "bcalm", "testing", "20-10-2020", 0, 0, 0, 0);

        Mockito.when(this.tweetRepository.findById(anyLong())).thenReturn(java.util.Optional.of(tweet));
        Mockito.when(this.tweetRepository.findAll()).thenReturn(Collections.singletonList(tweet));

        Assertions.assertThat(this.twitterService.deleteTweet("bcalm", 1L)).containsExactly(tweet);
    }
}
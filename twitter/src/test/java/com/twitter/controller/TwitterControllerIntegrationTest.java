package com.twitter.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.twitter.TwitterApplication;
import com.twitter.model.Tweet;
import com.twitter.model.Twitter;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = TwitterApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TwitterControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;
    private final HttpHeaders headers = new HttpHeaders();

    @Test
    public void shouldSaveTheUserDetails() throws JsonProcessingException {
        String url = this.createUrl("saveUserDetails");
        Twitter twitter = new Twitter("user", "Vikram", "20-10-2020", "url", "20-10-2000", "bio", 0, 0);
        String inputInJson = mapToJson(twitter);

        HttpEntity<Twitter> entity = new HttpEntity<>(twitter, headers);
        ResponseEntity<String> response = testRestTemplate.exchange(url, HttpMethod.POST, entity, String.class);
        String responseInJson = response.getBody();

        Assertions.assertThat(responseInJson).isEqualTo(inputInJson);
    }


    @Test
    public void shouldGiveTheUserDetails() throws JsonProcessingException {
        String url = createUrl("getUserDetails/bcalm");
        Twitter twitter = new Twitter("bcalm", "Vikram", "20-10-2020", "http://www.google.com", "20-10-2000", "Busy", 0, 0);
        String inputInJson = new ObjectMapper().writeValueAsString(twitter);


        HttpEntity<Object> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = testRestTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        String responseInJson = response.getBody();

        Assertions.assertThat(responseInJson).isEqualTo(inputInJson);

    }

    @Test
    public void shouldAddTheTweet() throws JsonProcessingException {
        String url = createUrl("addTweet");
        Tweet tweet = new Tweet("tweet", "test_user", "content", "ts", 0, 0, 0, 0);
        tweet.setId(2);
        List<Tweet> tweets = getDefaultTweets();
        tweets.add(tweet);
        String inputInJson = new ObjectMapper().writeValueAsString(tweets);

        headers.set("userId", "test_user");
        HttpEntity<Tweet> entity = new HttpEntity<>(tweet, headers);
        ResponseEntity<String> response = testRestTemplate.exchange(url, HttpMethod.POST, entity, String.class);
        String responseInJson = response.getBody();

        Assertions.assertThat(responseInJson).isEqualTo(inputInJson);
    }

    @Test
    public void shouldGiveAllTheTweets() throws JsonProcessingException {
        String url = createUrl("getTweets");
        Tweet tweet = new Tweet("tweet", "bcalm", "hello", "20-10-2020", 0, 0, 0, 0);
        tweet.setId(1);
        List<Tweet> tweets = Collections.singletonList(tweet);
        String expected = new ObjectMapper().writeValueAsString(tweets);

        headers.set("userId", "bcalm");
        HttpEntity<?> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = testRestTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        String actual = response.getBody();

        Assertions.assertThat(expected).isEqualTo(actual);
    }

    @Test
    public void shouldLikeTheTweet() throws JsonProcessingException {
        String url = createUrl("addLike");
        Tweet tweet = new Tweet("tweet", "bcalm", "hello", "20-10-2020", 0, 1, 0, 0);
        tweet.setId(2);

        headers.set("userId", "userId");
        HttpEntity<Long> entity = new HttpEntity<>(2L, headers);
        ResponseEntity<String> response = testRestTemplate.exchange(url, HttpMethod.POST, entity, String.class);

        Assertions.assertThat(new ObjectMapper().writeValueAsString(tweet)).isEqualTo(response.getBody());
    }

    @Test
    public void shouldADislikeTheTweet() throws JsonProcessingException {
        String url = createUrl("addLike");
        Tweet tweet = new Tweet("tweet", "bcalm", "hello", "20-10-2020", 0, 0, 0, 0);
        tweet.setId(2);

        headers.set("userId", "bcalm");
        HttpEntity<Long> entity = new HttpEntity<>(2L, headers);
        ResponseEntity<String> response = testRestTemplate.exchange(url, HttpMethod.POST, entity, String.class);

        Assertions.assertThat(new ObjectMapper().writeValueAsString(tweet)).isEqualTo(response.getBody());
    }



    //    @Test
//    public void shouldGiveAllLikedTweets() throws JsonProcessingException {
//        String url = createUrl("getLikeTweets");
//        Tweet tweet1 = new Tweet("testing", "Vikram", "bcalm", "20-10-2020");
//        Tweet tweet2 = new Tweet("testing", "Vikram", "bcalm", "20-10-2020");
//        tweet1.setId(1);
//        tweet2.setId(2);
//        List<Tweet> tweets = Arrays.asList(tweet2, tweet1);
//
//
//        HttpEntity<Object> entity = new HttpEntity<>(headers);
//        ResponseEntity<String> response = testRestTemplate.exchange(url, HttpMethod.GET, entity, String.class);
//
//        Assertions.assertThat(response.getBody()).isEqualTo(new ObjectMapper().writeValueAsString(tweets));
//    }
//
//    @Test
//    public void shouldAddTheRetweet() throws JsonProcessingException {
//        String url = createUrl("addRetweet/1");
//        Tweet tweet = new Tweet("testing", "Vikram", "bcalm", "20-10-2020");
//        tweet.setId(4);
//        List<Tweet> tweets = getDefaultTweets();
//        tweets.add(tweet);
//        HttpEntity<Tweet> entity = new HttpEntity<>(tweet, headers);
//        ResponseEntity<String> response = testRestTemplate.exchange(url, HttpMethod.POST, entity, String.class);
//
//        Assertions.assertThat(new ObjectMapper().writeValueAsString(tweets)).isEqualTo(response.getBody());
//    }
//
    private List<Tweet> getDefaultTweets() {
        Tweet tweet1 = new Tweet("tweet", "bcalm", "hello", "20-10-2020", 0, 0, 0, 0);
        tweet1.setId(1);
        return new ArrayList<>(Arrays.asList(tweet1));
    }

    private String createUrl(String uri) {
        return "http://localhost:" + port + "/api/" + uri;
    }


    private String mapToJson(Object object) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(object);
    }
}

package com.twitter.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.twitter.model.Tweet;
import com.twitter.model.Twitter;
import com.twitter.service.TwitterService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;

@RunWith(SpringRunner.class)
@WebMvcTest(value = TwitterController.class)
public class TwitterControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TwitterService twitterService;

    @Test
    public void shouldSaveTheTwitterDetails() throws Exception {
        String uri = "/api/saveUserDetails";
        Twitter twitter = new Twitter("user", "name", "joinedAt", "url", "dob", "bio", 0, 0);
        String inputInJson = this.mapToJson(twitter);

        Mockito.when(this.twitterService.saveUserDetails(any(Twitter.class))).thenReturn(twitter);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post(uri)
                .accept(MediaType.APPLICATION_JSON)
                .content(inputInJson)
                .contentType(MediaType.APPLICATION_JSON);


        assertExpected(inputInJson, requestBuilder);
    }

    @Test
    public void shouldGiveTheTwitterDetails() throws Exception {
        String url = "/api/getUserDetails/bcalm";
        Twitter twitter = new Twitter("bcalm", "Vikram Singh", "20-10-2029", "www.google.com", "20-10-2000", "Busy", 0, 0);
        String inputInJson = new ObjectMapper().writeValueAsString(twitter);

        Mockito.when(twitterService.getTwitterDetails("bcalm")).thenReturn(twitter);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(url);

        assertExpected(inputInJson, requestBuilder);
    }

    @Test
    public void shouldAddTheTweet() throws Exception {
        String url = "/api/addTweet";
        Tweet tweet = new Tweet("tweet", "test_user", "content", "ts", 0, 0, 0, 0);
        tweet.setId(1);
        List<Tweet> tweets = Collections.singletonList(tweet);
        String inputInJson = this.mapToJson(tweets);

        Mockito.when(this.twitterService.addTweet(any(), any(Tweet.class))).thenReturn(tweets);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post(url)
                .header("userId", "test_user")
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapToJson(tweet))
                .contentType(MediaType.APPLICATION_JSON);

        assertExpected(inputInJson, requestBuilder);
    }

    @Test
    public void shouldGiveAllTweets() throws Exception {
        String url = "/api/getTweets";
        Tweet tweet = new Tweet("tweet", "bcalm", "hello", "20-10-2020", 0, 0, 0, 0);
        List<Tweet> tweets = Collections.singletonList(tweet);
        String inputInJson = this.mapToJson(tweets);

        Mockito.when(twitterService.getTweets("bcalm")).thenReturn(tweets);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(url).header("userId", "bcalm");

        assertExpected(inputInJson, requestBuilder);
    }


    @Test
    public void shouldLikeTheTweet() throws Exception {
        String url = "/api/addLike";
        Tweet tweet = new Tweet("tweet", "bcalm", "hello", "20-10-2020", 0, 0, 0, 0);
        tweet.setId(1);

        Mockito.when(twitterService.toggleLike("bcalm", 1L)).thenReturn(tweet);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post(url)
                .header("userId", "bcalm")
                .accept(MediaType.APPLICATION_JSON)
                .content("1")
                .contentType(MediaType.APPLICATION_JSON);

        assertExpected(this.mapToJson(tweet), requestBuilder);
    }


    @Test
    public void shouldGetAllTweetsLikedByUser() throws Exception {
        String url = "/api/getLikeTweets";
        Tweet tweet = new Tweet("tweet", "bcalm", "hello", "20-10-2020", 0, 1, 0, 0);
        List<Tweet> tweets = Collections.singletonList(tweet);
        String inputInJson = new ObjectMapper().writeValueAsString(tweets);

        Mockito.when(twitterService.getLikeTweets("bcalm")).thenReturn(tweets);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(url).header("userId", "bcalm");

        assertExpected(inputInJson, requestBuilder);
    }

    @Test
    public void shouldDeleteTheTweet() throws Exception {
        String url = "/api/deleteTweet";
        Tweet tweet = new Tweet("tweet", "bcalm", "hello", "20-10-2020", 0, 0, 0, 0);
        List<Tweet> tweets = Collections.singletonList(tweet);
        String inputInJson = new ObjectMapper().writeValueAsString(tweets);

        Mockito.when(twitterService.deleteTweet("bcalm", 1L)).thenReturn(tweets);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post(url)
                .header("userId", "bcalm")
                .accept(MediaType.APPLICATION_JSON)
                .content("1")
                .contentType(MediaType.APPLICATION_JSON);

        assertExpected(inputInJson, requestBuilder);
    }

    private void assertExpected(String inputInJson, RequestBuilder requestBuilder) throws Exception {
        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        String outputInJson = response.getContentAsString();

        assertEquals(inputInJson, outputInJson);
    }


    private String mapToJson(Object object) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(object);
    }
}
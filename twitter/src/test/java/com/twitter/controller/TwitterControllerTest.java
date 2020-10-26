package com.twitter.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.twitter.model.Tweet;
import com.twitter.model.TweetActions;
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
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@RunWith(SpringRunner.class)
@WebMvcTest(value = TwitterController.class)
public class TwitterControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TwitterService twitterService;

    @Test
    public void shouldGiveTheTwitterDetails() throws Exception {
        String url = "/api/getUserDetails/bcalm";
        Twitter twitter = new Twitter("bcalm", "Vikram Singh", "20-10-2020", 0, 0);
        String inputInJson = new ObjectMapper().writeValueAsString(twitter);

        Mockito.when(twitterService.getTwitterDetails("bcalm")).thenReturn(twitter);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(url);

        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        String outputInJson = response.getContentAsString();

        assertEquals(inputInJson, outputInJson);
    }


    @Test
    public void shouldGetAllTweets() throws Exception {
        String url = "/api/getTweets";
        Tweet tweet = new Tweet("testing", "Vikram Singh", "bcalm", "20-10-2020");
        List<Tweet> tweets = new ArrayList<>();
        tweets.add(tweet);
        String inputInJson = new ObjectMapper().writeValueAsString(tweets);

        Mockito.when(twitterService.getTweets()).thenReturn(tweets);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(url);

        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        String outputInJson = response.getContentAsString();

        assertEquals(inputInJson, outputInJson);
    }

    @Test
    public void shouldGetAllRetweets() throws Exception {
        String url = "/api/getRetweets";
        Tweet tweet = new Tweet("testing", "Vikram Singh", "bcalm", "20-10-2020");
        List<Tweet> tweets = new ArrayList<>();
        tweets.add(tweet);
        String inputInJson = new ObjectMapper().writeValueAsString(tweets);

        Mockito.when(twitterService.getRetweets()).thenReturn(tweets);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(url);

        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        String outputInJson = response.getContentAsString();

        assertEquals(inputInJson, outputInJson);
    }

    @Test
    public void shouldGetAllTweetsLikedByUser() throws Exception {
        String url = "/api/getLikeTweets";
        Tweet tweet = new Tweet("testing", "Vikram Singh", "bcalm", "20-10-2020");
        List<Tweet> tweets = new ArrayList<>();
        tweets.add(tweet);
        String inputInJson = new ObjectMapper().writeValueAsString(tweets);

        Mockito.when(twitterService.getLikeTweets()).thenReturn(tweets);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(url);

        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        String outputInJson = response.getContentAsString();

        assertEquals(inputInJson, outputInJson);
    }

    @Test
    public void shouldAddTheTweet() throws Exception {
        String url = "/api/addTweet";
        Tweet tweet = new Tweet("testing", "Vikram Singh", "bcalm", "20-10-2020");
        tweet.setId(1);
        List<Tweet> tweets = new ArrayList<>();
        tweets.add(tweet);
        String inputInJson = new ObjectMapper().writeValueAsString(tweets);

        Mockito.when(this.twitterService.addTweet(any(Tweet.class))).thenReturn(tweets);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post(url)
                .accept(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(tweet))
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        String outputInJson = response.getContentAsString();

        assertEquals(inputInJson, outputInJson);
    }

    @Test
    public void shouldDeleteTheTweet() throws Exception {
        String url = "/api/deleteTweet";
        Tweet tweet = new Tweet("testing", "Vikram Singh", "bcalm", "20-10-2020");
        List<Tweet> tweets = new ArrayList<>();
        tweets.add(tweet);
        String inputInJson = new ObjectMapper().writeValueAsString(tweets);

        Mockito.when(twitterService.deleteTweet((long) 1.0)).thenReturn(tweets);
        RequestBuilder requestBuilder = post(url)
                .accept(MediaType.APPLICATION_JSON)
                .content("1")
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        String outputInJson = response.getContentAsString();

        assertEquals(inputInJson, outputInJson);
    }

    @Test
    public void shouldLikeTheTweet() throws Exception {
        String url = "/api/addLike";
        TweetActions tweetActions = new TweetActions(true, false, false, 1, 0, 0);
        String inputInJson = new ObjectMapper().writeValueAsString(tweetActions);

        Mockito.when(twitterService.toggleLike((long) 1)).thenReturn(tweetActions);
        RequestBuilder requestBuilder = post(url)
                .accept(MediaType.APPLICATION_JSON)
                .content("1")
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        String outputInJson = response.getContentAsString();

        assertEquals(inputInJson, outputInJson);
    }

    @Test
    public void shouldGiveAllActionsDoneByUSer() throws Exception {
        String url = "/api/getUserActionDetails";
        TweetActions tweetActions = new TweetActions(true, false, false, 1, 0, 0);
        String inputInJson = new ObjectMapper().writeValueAsString(tweetActions);

        Mockito.when(twitterService.getUserActionDetails((long) 1)).thenReturn(tweetActions);
        RequestBuilder requestBuilder = post(url)
                .accept(MediaType.APPLICATION_JSON)
                .content("1")
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        String outputInJson = response.getContentAsString();

        assertEquals(inputInJson, outputInJson);
    }

    @Test
    public void shouldAddTheRetweet() throws Exception {
        String url = "/api/addRetweet/1";
        Tweet tweet = new Tweet("testing", "Vikram Singh", "bcalm", "20-10-2020");
        List<Tweet> tweets = new ArrayList<>();
        tweets.add(tweet);
        String inputInJson = new ObjectMapper().writeValueAsString(tweets);

        Mockito.when(twitterService.addRetweet(any(Tweet.class), anyLong())).thenReturn(tweets);
        RequestBuilder requestBuilder = post(url)
                .accept(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(tweet))
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        String outputInJson = response.getContentAsString();

        assertEquals(inputInJson, outputInJson);
    }
}
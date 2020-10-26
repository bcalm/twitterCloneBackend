package com.twitter.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.twitter.model.Tweet;
import com.twitter.model.TweetActions;
import com.twitter.model.Twitter;
import com.twitter.service.TwitterService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
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
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@WebMvcTest(value = TwitterController.class)
public class TwitterControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TwitterService twitterService;

    @Test
    public void shouldGiveTheTwitterDetails() throws Exception {
        Twitter twitter = new Twitter("bcalm", "Vikram Singh", "20-10-2020", 0, 0);
        String inputInJson = new ObjectMapper().writeValueAsString(twitter);

        String url = "/api/getUserDetails/bcalm";

        Mockito.when(twitterService.getTwitterDetails("bcalm")).thenReturn(twitter);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(url);

        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        String outputInJson = response.getContentAsString();

        assertEquals(inputInJson, outputInJson);
    }


    @Test
    public void shouldGetAllTweets() throws Exception {
        Tweet tweet = new Tweet("testing", "Vikram Singh", "bcalm", "20-10-2020");
        List<Tweet> tweets = new ArrayList<>();
        tweets.add(tweet);

        String inputInJson = new ObjectMapper().writeValueAsString(tweets);

        Mockito.when(twitterService.getTweets()).thenReturn(tweets);
        String url = "/api/getTweets";

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(url);

        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        String outputInJson = response.getContentAsString();

        assertEquals(inputInJson, outputInJson);
    }

    @Test
    public void shouldGetAllRetweets() throws Exception {
        Tweet tweet = new Tweet("testing", "Vikram Singh", "bcalm", "20-10-2020");
        List<Tweet> tweets = new ArrayList<>();
        tweets.add(tweet);

        String inputInJson = new ObjectMapper().writeValueAsString(tweets);

        Mockito.when(twitterService.getRetweets()).thenReturn(tweets);
        String url = "/api/getRetweets";

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(url);

        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        String outputInJson = response.getContentAsString();

        assertEquals(inputInJson, outputInJson);
    }

    @Test
    public void shouldGetAllTweetsLikedByUser() throws Exception {
        Tweet tweet = new Tweet("testing", "Vikram Singh", "bcalm", "20-10-2020");
        List<Tweet> tweets = new ArrayList<>();
        tweets.add(tweet);

        String inputInJson = new ObjectMapper().writeValueAsString(tweets);

        Mockito.when(twitterService.getLikeTweets()).thenReturn(tweets);
        String url = "/api/getLikeTweets";

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(url);

        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        String outputInJson = response.getContentAsString();

        assertEquals(inputInJson, outputInJson);
    }

//    @Test
//    public void shouldAddTheTweet() throws Exception {
//        Tweet tweet = new Tweet("testing", "Vikram Singh", "bcalm", "20-10-2020");
//        List<Tweet> tweets = new ArrayList<>();
//        tweets.add(tweet);
//
//        String inputInJson = new ObjectMapper().writeValueAsString(tweets);
//
//        Mockito.when(twitterService.addTweet("testing")).thenReturn(tweets);
//        String url = "/api/addTweet";
//
//        RequestBuilder requestBuilder = MockMvcRequestBuilders.post(url)
//                .accept(MediaType.APPLICATION_JSON)
//                .content("testing")
//                .contentType(MediaType.APPLICATION_JSON);
//
//        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
//        MockHttpServletResponse response = mvcResult.getResponse();
//
//        String outputInJson = response.getContentAsString();
//
//        assertEquals(inputInJson, outputInJson);
//    }

    @Test
    public void shouldDeleteTheTweet() throws Exception {
        Tweet tweet = new Tweet("testing", "Vikram Singh", "bcalm", "20-10-2020");
        List<Tweet> tweets = new ArrayList<>();
        tweets.add(tweet);

        String inputInJson = new ObjectMapper().writeValueAsString(tweets);

        Mockito.when(twitterService.deleteTweet((long) 1.0)).thenReturn(tweets);
        String url = "/api/deleteTweet";

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post(url)
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
        TweetActions tweetActions = new TweetActions(true, false, false, 1, 0, 0);
        String inputInJson = new ObjectMapper().writeValueAsString(tweetActions);

        Mockito.when(twitterService.toggleLike((long) 1)).thenReturn(tweetActions);
        String url = "/api/addLike";

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post(url)
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
        TweetActions tweetActions = new TweetActions(true, false, false, 1, 0, 0);
        String inputInJson = new ObjectMapper().writeValueAsString(tweetActions);

        Mockito.when(twitterService.getUserActionDetails((long) 1)).thenReturn(tweetActions);
        String url = "/api/getUserActionDetails";

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post(url)
                .accept(MediaType.APPLICATION_JSON)
                .content("1")
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        String outputInJson = response.getContentAsString();

        assertEquals(inputInJson, outputInJson);
    }

//    @Test
//    public void shouldAddTheRetweet() throws Exception {
//        Tweet tweet = new Tweet("testing", "Vikram Singh", "bcalm", "20-10-2020");
//        List<Tweet> tweets = new ArrayList<>();
//        tweets.add(tweet);
//
//        String inputInJson = new ObjectMapper().writeValueAsString(tweets);
//
//        Mockito.when(twitterService.addRetweet((long) 1.0)).thenReturn(tweets);
//        String url = "/api/addRetweet";
//
//        RequestBuilder requestBuilder = MockMvcRequestBuilders.post(url)
//                .accept(MediaType.APPLICATION_JSON)
//                .content("1")
//                .contentType(MediaType.APPLICATION_JSON);
//
//        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
//        MockHttpServletResponse response = mvcResult.getResponse();
//        String outputInJson = response.getContentAsString();
//
//        assertEquals(inputInJson, outputInJson);
//    }
}
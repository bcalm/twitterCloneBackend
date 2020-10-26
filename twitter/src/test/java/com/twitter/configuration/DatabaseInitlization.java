package com.twitter.configuration;

import com.twitter.model.Tweet;
import com.twitter.model.TweetActions;
import com.twitter.model.Twitter;
import com.twitter.repository.TweetActionRepository;
import com.twitter.repository.TweetRepository;
import com.twitter.repository.TwitterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class DatabaseInitialisation {

    @Autowired
    private TwitterRepository twitterRepository;

    @Autowired
    private TweetRepository tweetRepository;

    @Autowired
    private TweetActionRepository tweetActionRepository;

    @Bean
    public void userInitiliser(){
        Twitter twitter = new Twitter("bcalm", "Vikram", "20-10-2020", 0, 0);
        this.twitterRepository.save(twitter);

    }

    @Bean
    public void tweetIniiliser(){
        Tweet tweet1 = new Tweet("testing", "Vikram", "bcalm", "20-10-2020");
        this.tweetRepository.save(tweet1);

        TweetActions tweetActions1 = new TweetActions(false, false, false, 0, 0, 0);
        this.tweetActionRepository.save(tweetActions1);

        Tweet tweet2 = new Tweet("testing", "Vikram", "bcalm", "20-10-2020");
        this.tweetRepository.save(tweet2);

        TweetActions tweetActions2 = new TweetActions(true, false, false, 1, 0, 0);
        this.tweetActionRepository.save(tweetActions2);

        Tweet tweet3 = new Tweet("testing", "Vikram", "bcalm", "20-10-2020");
        this.tweetRepository.save(tweet3);

        TweetActions tweetActions3 = new TweetActions(false, true, false, 0, 0, 1);
        this.tweetActionRepository.save(tweetActions3);
    }
}



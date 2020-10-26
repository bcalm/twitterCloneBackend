package com.twitter.repository;

import com.twitter.model.Twitter;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.Optional;


@RunWith(SpringRunner.class)
@DataJpaTest
public class TwitterRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private TwitterRepository twitterRepository;

    @Test
    public void shouldSaveTheTwitterDetailsIntoDb(){
        Twitter twitter = new Twitter("bcalm", "Vikram", "20-10-2020", 0, 0);
        Twitter savedInDb = testEntityManager.persist(twitter);

        Optional<Twitter> getFromDb = this.twitterRepository.findById(twitter.getId());
        getFromDb.ifPresent(twitter1->{
            Assertions.assertThat(twitter1).isEqualTo(savedInDb);
        });
    }
}
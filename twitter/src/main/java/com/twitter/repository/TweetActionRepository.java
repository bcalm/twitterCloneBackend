package com.twitter.repository;

import com.twitter.model.TweetActions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TweetActionRepository extends JpaRepository<TweetActions, Long> {
}

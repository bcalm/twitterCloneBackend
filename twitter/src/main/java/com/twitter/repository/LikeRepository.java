package com.twitter.repository;

import com.twitter.model.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    List<Like> findAllByTweetId(long id);
    List<Like> findAllByUserId(String userId);
    @Transactional
    void deleteByTweetIdAndUserId(long id, String userId);
}

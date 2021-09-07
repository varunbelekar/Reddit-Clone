package com.reddit.repository;

import com.reddit.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    Post findBySubRedditSubredditId(String subredditId);

    List<Post> findByUserUserName(String userName);
}

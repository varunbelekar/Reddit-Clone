package com.reddit.service;

import com.reddit.dto.PostRequest;
import com.reddit.exception.SpringRedditException;
import com.reddit.model.Post;
import com.reddit.model.SubReddit;
import com.reddit.model.User;
import com.reddit.repository.PostRepository;
import com.reddit.repository.SubredditRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class PostService {
    private final PostRepository postRepository;
    private final SubredditRepository subredditRepository;
    private final AuthService authService;

    public Post save(PostRequest postRequest) {
        SubReddit subReddit = subredditRepository.findByName(postRequest.getSubredditName()).orElseThrow(() -> new SpringRedditException("No subreddit found : " + postRequest.getSubredditName()));
        User currentUser = authService.getCurretUser();
        Post post = mapPostRequestToPost(postRequest, subReddit, currentUser);
        return postRepository.save(post);
    }

    public Post mapPostRequestToPost(PostRequest postRequest, SubReddit subReddit, User user){
        Post post = new Post();
        post.setPostName(postRequest.getPostName());
        post.setDescription(postRequest.getDescription());
        post.setCreatedDate(Instant.now());
        post.setSubReddit(subReddit);
        post.setUser(user);
        return post;
    }

    public List<Post> getPostUserName(String userName) {
        return postRepository.findByUserUserName(userName);
    }

    public Post getPostBySubredditId(String subredditId) {
        return postRepository.findBySubRedditSubredditId(subredditId);
    }

    public List<Post> findAllPosts() {
        return postRepository.findAll();
    }
}

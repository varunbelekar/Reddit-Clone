package com.reddit.service;

import com.reddit.dto.PostRequest;
import com.reddit.dto.PostResponse;
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
import java.util.stream.Collectors;

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
        post.setUrl(postRequest.getUrl());
        post.setSubReddit(subReddit);
        post.setUser(user);
        return post;
    }

   /* public List<Post> getPostUserName(String userName) {
        return postRepository.findByUserUserName(userName);
    }

    public Post getPostBySubredditName(String subredditName) {
        return postRepository.findBySubRedditSubredditId(subredditName);
    }*/

    public List<PostResponse> findAllPosts() {
        return postRepository.findAll().stream()
                .map(this::toPostResponse)
                .collect(Collectors.toList());
    }

    public PostResponse toPostResponse(Post post){
        PostResponse postResponse = new PostResponse();
        postResponse.setId(post.getPostId());
        postResponse.setPostName(post.getPostName());
        postResponse.setDescription(post.getDescription());
        postResponse.setUserName(post.getUser().getUserName());
        postResponse.setSubredditName(post.getSubReddit().getName());
        postResponse.setUrl(post.getUrl());
        postResponse.setVoteCount(post.getVoteCount());
        postResponse.setCommentCount(post.getCommentCount());
        return postResponse;
    }

    public void deleteAllPosts() {
        postRepository.deleteAll();
    }
}

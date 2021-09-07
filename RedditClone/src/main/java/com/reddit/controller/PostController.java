package com.reddit.controller;

import com.reddit.dto.PostRequest;
import com.reddit.model.Post;
import com.reddit.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@AllArgsConstructor
public class PostController {
    private final PostService postService;

    @PostMapping
    public Post savePost(@RequestBody PostRequest postRequest){
        return postService.save(postRequest);
    }

    @GetMapping
    public List<Post> getAllPosts(){
        return postService.findAllPosts();
    }

    @GetMapping("/subreddit/{subredditId}")
    public Post getPostBySubredditId(@PathVariable String subredditId){
        return postService.getPostBySubredditId(subredditId);
    }

    @GetMapping("/user/{userName}")
    public List<Post> getPostUserName(@PathVariable String userName){
        return postService.getPostUserName(userName);
    }

}

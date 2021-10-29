package com.reddit;

import com.reddit.dto.PostRequest;
import com.reddit.model.Post;
import com.reddit.model.SubReddit;
import com.reddit.model.User;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MockedValuesFactory {
    public static  List<Post> getPosts(){
        User user = User.builder()
                .userId(2132L)
                .userName("Varun")
                .build();
        SubReddit subReddit = SubReddit.builder()
                .name("Technology")
                .build();

        Post p1 = Post.builder()
                .postId(111L)
                .createdDate(Instant.now())
                .description("Create some dummy projects")
                .postName("How to master spring boot")
                .subReddit(subReddit)
                .user(user)
                .build();

        Post p2 = Post.builder()
                .postId(111L)
                .subReddit(subReddit)
                .createdDate(Instant.now())
                .user(user)
                .description("Create some dummy projects")
                .postName("How to master Angular")
                .build();

        List<Post> postList = new ArrayList<>();
        postList.add(p1);
        postList.add(p2);
        return postList;
    }

    public static Optional<Post> findPostById(){
        User user = User.builder()
                .userId(2132L)
                .userName("Varun")
                .build();
        SubReddit subReddit = SubReddit.builder()
                .name("Technology")
                .build();
        Post p2 = Post.builder()
                .postId(111L)
                .subReddit(subReddit)
                .createdDate(Instant.now())
                .user(user)
                .description("Create some dummy projects")
                .postName("How to master Angular")
                .build();
        return Optional.of(p2);
    }

    public static PostRequest getPostRequest(){
        PostRequest postRequest = PostRequest.builder()
                .postId(777L)
                .postName("How to learn spring boot")
                .description("Create dummy projects")
                .subredditName("Technology")
                .build();

        return postRequest;
    }

    public static Optional<SubReddit> getSubReddit(){
        SubReddit subReddit = SubReddit.builder()
                .name("Technology")
                .build();
        return Optional.of(subReddit);
    }

    public static User getCurrentUser(){
        User user = User.builder()
                .userId(2132L)
                .userName("Varun")
                .build();
        return user;
    }
}

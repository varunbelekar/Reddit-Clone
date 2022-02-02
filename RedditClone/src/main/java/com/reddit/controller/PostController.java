package com.reddit.controller;

import com.reddit.dto.PostRequest;
import com.reddit.dto.PostResponse;
import com.reddit.model.Post;
import com.reddit.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@AllArgsConstructor
public class PostController {
    private final PostService postService;

    @PostMapping
    public ResponseEntity<Long> savePost(@RequestBody PostRequest postRequest){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(postService.save(postRequest).getPostId());
    }

    @GetMapping
    public ResponseEntity<List<PostResponse>> getAllPosts(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(postService.findAllPosts());
    }

    @GetMapping("/paginated")
    public ResponseEntity<List<PostResponse>> getAllPostsPaginated(@RequestParam int pageNumber, @RequestParam int pageSize){
        return ResponseEntity.status(HttpStatus.OK)
                .body(postService.findAllPostsPaginated(pageNumber, pageSize));
    }

   /* @GetMapping("/subreddit/{subredditName}")
    public Post getPostBySubredditId(@PathVariable String subredditName){
        return postService.getPostBySubredditName(subredditName);
    }

    @GetMapping("/user/{userName}")
    public List<Post> getPostUserName(@PathVariable String userName){
        return postService.getPostUserName(userName);
    }*/

    @GetMapping("/{postId}")
    public  ResponseEntity<PostResponse> getPostById(@PathVariable Long postId){
        PostResponse postResponse = postService.findByPostId(postId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(postResponse);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteAllPosts(){
         postService.deleteAllPosts();
         return ResponseEntity.status(HttpStatus.OK).body("Deleted All posts");
    }

}

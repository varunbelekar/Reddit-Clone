package com.reddit.controller;

import com.reddit.dto.CommentDto;
import com.reddit.model.Comment;
import com.reddit.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;

@RestController
@RequestMapping("/api/comments")
@AllArgsConstructor
public class CommentsController {
    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<Void> saveComment(CommentDto commentDto){
        commentService.saveComment(commentDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(value = "/by-post/{postId}")
    public ResponseEntity<List<CommentDto>> getAllCommentsForPost(@PathVariable Long postId){
        return ResponseEntity.status(HttpStatus.OK)
                .body(commentService.getAllCommentsForPost(postId));
    }

    @GetMapping(value = "/by-user/{userName}")
    public ResponseEntity<List<CommentDto>> getAllCommentsByUserName(String userName){
        return ResponseEntity.status(HttpStatus.OK)
                .body(commentService.getAllCommentsByUserName(userName));
    }

    @DeleteMapping(value = "/{commentId}")
    public ResponseEntity<String> deleteCommentById(@PathVariable Long commentId){
        commentService.deleteComment(commentId);
        return ResponseEntity.status(HttpStatus.OK)
                .body("Successfully deleted comment");
    }
}

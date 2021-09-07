package com.reddit.service;

import com.reddit.dto.CommentDto;
import com.reddit.exception.PostNotFoundException;
import com.reddit.exception.SpringRedditException;
import com.reddit.model.Comment;
import com.reddit.model.Post;
import com.reddit.model.User;
import com.reddit.repository.CommentRepository;
import com.reddit.repository.PostRepository;
import com.reddit.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CommentService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final AuthService authService;
    private final CommentRepository commentRepository;

    public Long saveComment(CommentDto commentDto){
        Post post = postRepository.findById(commentDto.getPostId())
                .orElseThrow(() -> new PostNotFoundException("No post found : " + commentDto.getPostId().toString()));
        User user = authService.getCurretUser();
        Comment comment = mapCommentDtoToComment(commentDto, post, user);
        return commentRepository.save(comment).getCommentId();
    }

    private Comment mapCommentDtoToComment(CommentDto commentDto, Post post, User user){
        Comment comment = new Comment();
        comment.setCreatedDate(Instant.now());
        comment.setPost(post);
        comment.setUser(user);
        comment.setText(commentDto.getText());
        return comment;
    }

    public List<CommentDto> getAllCommentsForPost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException("No post found for id: " + postId));
        return commentRepository.findByPost(post)
                .stream()
                .map(this::mapCommentToCommentDto)
                .collect(Collectors.toList());

    }

    private CommentDto mapCommentToCommentDto(Comment comment){
        CommentDto commentDto = new CommentDto();
        commentDto.setPostId(comment.getPost().getPostId());
        commentDto.setText(comment.getText());
        commentDto.setCreatedDate(comment.getCreatedDate());
        commentDto.setUserName(comment.getUser().getUserName());
        commentDto.setId(comment.getCommentId());
        return commentDto;
    }

    public List<CommentDto> getAllCommentsByUserName(String userName) {
        User user = userRepository.findByUserName(userName).orElseThrow(() -> new SpringRedditException("User not found : " + userName));
        return commentRepository.findByUser(user)
                .stream()
                .map(this::mapCommentToCommentDto)
                .collect(Collectors.toList());
    }
}

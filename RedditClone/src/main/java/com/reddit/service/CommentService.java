package com.reddit.service;

import com.reddit.dto.CommentDto;
import com.reddit.exception.PostNotFoundException;
import com.reddit.exception.SpringRedditException;
import com.reddit.mapper.CommentMapper;
import com.reddit.model.Comment;
import com.reddit.model.Post;
import com.reddit.model.User;
import com.reddit.repository.CommentRepository;
import com.reddit.repository.PostRepository;
import com.reddit.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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

    @Transactional
    public Long saveComment(CommentDto commentDto){
        Post post = postRepository.findById(commentDto.getPostId())
                .orElseThrow(() -> new PostNotFoundException("No post found : " + commentDto.getPostId().toString()));
        post.setCommentCount(post.getCommentCount() + 1);
        postRepository.save(post);
        User user = authService.getCurrentUser();
        Comment comment = Mappers.getMapper(CommentMapper.class).commentDtoToComment(commentDto, post, user, Instant.now());
        return commentRepository.save(comment).getCommentId();
    }

    public List<CommentDto> getAllCommentsForPost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException("No post found for id: " + postId));
        return commentRepository.findByPost(post)
                .stream()
                .map(comment -> Mappers.getMapper(CommentMapper.class).mapCommentToCommentDto(comment, comment.getUser().getUserName(), comment.getPost().getPostId()))
                .collect(Collectors.toList());

    }


    public List<CommentDto> getAllCommentsByUserName(String userName) {
        User user = userRepository.findByUserName(userName).orElseThrow(() -> new SpringRedditException("User not found : " + userName));
        return commentRepository.findByUser(user)
                .stream()
                .map(comment -> Mappers.getMapper(CommentMapper.class).mapCommentToCommentDto(comment, comment.getUser().getUserName(), comment.getPost().getPostId()))
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new SpringRedditException("No comment found : " + commentId));
        Post post = postRepository.findById(comment.getPost().getPostId()).orElseThrow(() -> new SpringRedditException("No post found"));
        post.setCommentCount(post.getCommentCount() - 1);
        postRepository.save(post);
        commentRepository.deleteById(commentId);
    }
}

package com.reddit.mapper;

import com.reddit.dto.CommentDto;
import com.reddit.model.Comment;
import com.reddit.model.Post;
import com.reddit.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.time.Instant;

@Mapper
public interface CommentMapper {
    @Mappings({
            @Mapping(source = "user", target = "user"),
            @Mapping(source = "post", target = "post"),
            @Mapping(source = "instant", target = "createdDate")
    })
    Comment commentDtoToComment(CommentDto commentDto, Post post, User user, Instant instant);

    @Mappings({
            @Mapping(source = "comment.commentId", target = "id")
    })
    CommentDto mapCommentToCommentDto(Comment comment, String userName, Long postId);
}

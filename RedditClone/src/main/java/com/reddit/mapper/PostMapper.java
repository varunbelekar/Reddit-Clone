package com.reddit.mapper;

import com.reddit.dto.PostRequest;
import com.reddit.dto.PostResponse;
import com.reddit.model.Post;
import com.reddit.model.SubReddit;
import com.reddit.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.time.Instant;

@Mapper
public interface PostMapper {
    @Mappings(
            @Mapping(source = "post.postId" , target = "id")
    )
    PostResponse mapPostToPostResponse(Post post, String userName, String subredditName);

    @Mappings({
            @Mapping(source = "postRequest.description", target = "description")
    })
    Post mapPostRequestToPost(PostRequest postRequest, SubReddit subReddit, User user, Instant createdDate, int voteCount);
 }

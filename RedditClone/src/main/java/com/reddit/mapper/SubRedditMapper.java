package com.reddit.mapper;

import com.reddit.dto.SubRedditDto;
import com.reddit.model.SubReddit;
import com.reddit.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.time.Instant;

@Mapper
public interface SubRedditMapper {
    SubReddit mapSubRedditDtoToSubReddit(SubRedditDto subRedditDto, Instant createdDate, User user);


    SubRedditDto mapSubRedditToSubRedditDto(SubReddit subReddit, int numberOfPosts, Long id);
}

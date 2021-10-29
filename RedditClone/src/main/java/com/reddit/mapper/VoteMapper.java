package com.reddit.mapper;

import com.reddit.dto.VoteDto;
import com.reddit.model.Post;
import com.reddit.model.User;
import com.reddit.model.Vote;
import org.mapstruct.Mapper;

@Mapper
public interface VoteMapper {
    Vote mapVoteDtoToVote(VoteDto voteDto, Post post, User user);
}

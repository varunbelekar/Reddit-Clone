package com.reddit.service;

import com.reddit.dto.VoteDto;
import com.reddit.exception.PostNotFoundException;
import com.reddit.exception.SpringRedditException;
import com.reddit.model.Post;
import com.reddit.model.User;
import com.reddit.model.Vote;
import com.reddit.model.VoteType;
import com.reddit.repository.PostRepository;
import com.reddit.repository.VoteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@AllArgsConstructor
public class VoteService {
    private final VoteRepository voteRepository;
    private final PostRepository postRepository;
    private final AuthService authService;

    @Transactional
    public void vote(VoteDto voteDto) {
        Post post = postRepository.findById(voteDto.getPostId()).orElseThrow(() -> new PostNotFoundException("Post not found exception : " + voteDto.getPostId()));
        User currentUser = authService.getCurrentUser();
        Optional<Vote> voteByUserAndPost = voteRepository.findTopByPostAndUserOrderByVoteIdDesc(post, currentUser);
        if (voteByUserAndPost.isPresent()
        && voteByUserAndPost.get().getVoteType().equals(voteDto.getVoteType())){
            throw new SpringRedditException("You have already voted " + voteDto.getVoteType() + "'d for this post");
        }

        if(voteDto.getVoteType().equals(VoteType.UPVOTE)  ){
            post.setVoteCount(post.getVoteCount() + 1);
        }else{
            post.setVoteCount(post.getVoteCount() - 1);
        }
        Vote vote = toVote(voteDto, post, currentUser);
        voteRepository.save(vote);
        postRepository.save(post);
    }

    public Vote toVote(VoteDto voteDto, Post post, User user){
        return Vote.builder()
                .post(post)
                .user(user)
                .voteType(voteDto.getVoteType())
                .build();
    }

    public void deleteAllVotes() {
        voteRepository.deleteAll();
    }
}

package com.reddit.service;

import com.reddit.dto.SubRedditDto;
import com.reddit.mapper.SubRedditMapper;
import com.reddit.model.SubReddit;
import com.reddit.repository.SubredditRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class SubredditService {
    private final SubredditRepository subredditRepository;
    private final AuthService authService;

    @Transactional
    public SubRedditDto save(SubRedditDto subRedditDto){
        SubReddit subReddit = subredditRepository.save(Mappers.getMapper(SubRedditMapper.class).mapSubRedditDtoToSubReddit(subRedditDto, Instant.now(), authService.getCurrentUser()));
        subRedditDto.setId(subReddit.getSubredditId());
        return subRedditDto;
    }

    /*public SubReddit toSubReddit(SubRedditDto dto){
        SubReddit subReddit = new SubReddit();
        subReddit.setName(dto.getName());
        subReddit.setDescription(dto.getDescription());
        subReddit.setCreatedDate(Instant.now());
        subReddit.setUser(authService.getCurrentUser());
        return subReddit;
    }*/

    @Transactional
    public List<SubRedditDto> getAllSubreddits(){
        return subredditRepository.findAll()
                .stream().map(subReddit -> Mappers.getMapper(SubRedditMapper.class).mapSubRedditToSubRedditDto(subReddit, subReddit.getPosts().size(), subReddit.getSubredditId()))
                .collect(Collectors.toList());
    }

    /*public SubRedditDto toSubRedditDto(SubReddit subReddit){
        SubRedditDto dto = new SubRedditDto();
        dto.setId(subReddit.getSubredditId());
        dto.setDescription(subReddit.getDescription());
        dto.setNumberOfPosts(subReddit.getPosts().size());
        dto.setName(subReddit.getName());
        return dto;
    }*/

    public void deleteSubredditById(Long subredditId) {
         subredditRepository.deleteById(subredditId);
    }
}

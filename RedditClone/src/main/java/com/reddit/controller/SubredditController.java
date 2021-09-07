package com.reddit.controller;

import com.reddit.dto.SubRedditDto;
import com.reddit.model.SubReddit;
import com.reddit.service.SubredditService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subreddit")
@AllArgsConstructor
@Slf4j
public class SubredditController {
    private final SubredditService subredditService;

    @PostMapping("/")
    public ResponseEntity<SubRedditDto> createSubreddit(@RequestBody SubRedditDto subRedditDto){
        return ResponseEntity.status(HttpStatus.OK)
                .body(subredditService.save(subRedditDto));
    }

    @GetMapping("/")
    public ResponseEntity<List<SubRedditDto>> getAllSubRedditDto(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(subredditService.getAllSubreddits());
    }
}

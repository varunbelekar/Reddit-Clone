package com.reddit.service;

import com.reddit.MockedValuesFactory;
import com.reddit.dto.PostRequest;
import com.reddit.dto.PostResponse;
import com.reddit.exception.PostNotFoundException;
import com.reddit.mapper.PostMapper;
import com.reddit.model.User;
import com.reddit.repository.PostRepository;
import com.reddit.repository.SubredditRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
public class PostServiceTest {

    @Mock
    private PostRepository postRepository;

    @Mock
    private SubredditRepository subredditRepository;

    @Mock
    private AuthService authService;

    @InjectMocks
    private PostService postService;

    @Test
    public void savePostTest(){
        PostRequest postRequest = MockedValuesFactory.getPostRequest();
        Mockito.when(subredditRepository.findByName(postRequest.getSubredditName())).thenReturn(MockedValuesFactory.getSubReddit());
        Mockito.when(authService.getCurrentUser()).thenReturn(MockedValuesFactory.getCurrentUser());
        postService.save(postRequest);
        Mockito.verify(postRepository, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    public void findAllPostsTest(){
        Mockito.when(postRepository.findAll()).thenReturn(MockedValuesFactory.getPosts());
        List<PostResponse> postResponses = postService.findAllPosts();
        assertNotNull(postResponses);
    }

    @Test
    public void findCountOfAllPosts(){
        Mockito.when(postRepository.findAll()).thenReturn(MockedValuesFactory.getPosts());
        List<PostResponse> postResponses = postService.findAllPosts();
        assertEquals(2, postResponses.size());
    }

    @Test
    public void findPostByIdTest(){
        Mockito.when(postRepository.findById(Mockito.anyLong())).thenReturn(MockedValuesFactory.findPostById());
        assertNotNull(postService.findByPostId(111L));
    }

    @Test
    public void findPostByIdNotFound(){
        Mockito.when(postRepository.findById(222L)).thenReturn(Optional.empty());
        assertThrows(PostNotFoundException.class, () -> postService.findByPostId(222L));
    }

    @Test
    public void deleteAllPostsTest(){
        postService.deleteAllPosts();
        Mockito.verify(postRepository, Mockito.times(1)).deleteAll();
    }
}

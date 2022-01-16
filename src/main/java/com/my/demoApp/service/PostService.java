package com.my.demoApp.service;

import com.my.demoApp.dto.PostDto;
import com.my.demoApp.exception.PostNotFoundException;
import com.my.demoApp.model.Post;
import com.my.demoApp.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class PostService {

    @Autowired
    private AuthService authService;
    @Autowired
    private PostRepository postRepository;

    public void createPost(PostDto postDto) {
        Post post = mapFromDtoToPost(postDto);
        postRepository.save(post);
    }

    public PostDto readSinglePost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(()->
                new PostNotFoundException("For id " + id));
        return mapFromPostToDto(post);
    }
    
    public List<PostDto> showAllPosts() {
        List<Post> posts = postRepository.findAll();
        return posts.stream().map(this::mapFromPostToDto).collect(toList());
    }

    private PostDto mapFromPostToDto(Post post) {
        PostDto postDto = new PostDto();
        postDto.setId(post.getId());
        postDto.setTitle(post.getTitle());
        postDto.setContent(post.getContent());
        postDto.setUsername(post.getUsername());
        return postDto;
    }

    private Post mapFromDtoToPost(PostDto postDto) {
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setCreatedOn(Instant.now());
        post.setUpdatedOn(Instant.now());
        User loggedInUser = authService.getCurrentUser().orElseThrow(()->
                new IllegalArgumentException("User not found!"));
        post.setUsername(loggedInUser.getUsername());
        return post;
    }
}

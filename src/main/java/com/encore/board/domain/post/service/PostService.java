package com.encore.board.domain.post.service;

import com.encore.board.domain.author.domain.Author;
import com.encore.board.domain.author.repository.AuthorRepository;
import com.encore.board.domain.post.domain.Post;
import com.encore.board.domain.post.dto.PostReqCreateDto;
import com.encore.board.domain.post.dto.PostReqUpdateDto;
import com.encore.board.domain.post.dto.PostResDetailDto;
import com.encore.board.domain.post.dto.PostResListDto;
import com.encore.board.domain.post.repository.PostRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@Slf4j
public class PostService {
    private final AuthorRepository authorRepository;
    private final PostRepository postRepository;
    @Autowired
    public PostService(AuthorRepository authorRepository, PostRepository postRepository) {
        this.authorRepository = authorRepository;
        this.postRepository = postRepository;
    }

    public List<PostResListDto> getPostList() {
//        List<Post> postList = postRepository.findAll(Sort.by(Sort.Direction.DESC, "createdTime"));
        List<Post> postList = postRepository.findAllByOrderByCreatedTimeDesc();
        List<PostResListDto> postResListDtos = new ArrayList<>();
        for(Post post : postList){
            PostResListDto postResListDto = PostResListDto.builder()
                    .id(post.getId())
                    .title(post.getTitle())
                    .author_email(post.getAuthor() == null ? "익명" : post.getAuthor().getEmail())
                    .build();
            postResListDtos.add(postResListDto);
        }
        return postResListDtos;
    }

    public PostResDetailDto findById(Long id) throws EntityNotFoundException{
        Post post = postRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        PostResDetailDto postResDetailDto = PostResDetailDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .contents(post.getContents())
                .createdTime(post.getCreatedTime())
                .build();
        return postResDetailDto;
    }

    public void postWrite(PostReqCreateDto postReqCreateDto) {
        Author author = authorRepository.findByEmail(postReqCreateDto.getEmail()).orElse(null);
        Post post = Post.builder()
                .title(postReqCreateDto.getTitle())
                .contents(postReqCreateDto.getContents())
                .author(author)
                .build();
        postRepository.save(post);
    }

    public void delete(Long id) throws EntityNotFoundException{
        postRepository.delete(postRepository.findById(id).orElseThrow(EntityNotFoundException::new));
    }

    public void postUpdate(Long id, PostReqUpdateDto postReqUpdateDto) {
        Post post = postRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        post.setTitle(postReqUpdateDto.getTitle());
        post.setContents(postReqUpdateDto.getContents());
        postRepository.save(post);
    }
}

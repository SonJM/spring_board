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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@Slf4j
public class PostService {
    private final AuthorRepository authorRepository;
    private final PostRepository postRepository;
    @Autowired
    public PostService(AuthorRepository authorRepository, PostRepository postRepository) {
        this.authorRepository = authorRepository;
        this.postRepository = postRepository;
    }

    public Page<PostResListDto> findAll(Pageable pageable) {
//        List<Post> postList = postRepository.findAll(Sort.by(Sort.Direction.DESC, "createdTime"));
//        List<Post> postList = postRepository.findAllByOrderByCreatedTimeDesc();
//        List<Post> postList = postRepository.findAllFetchJoin();
        Page<Post> posts = postRepository.findAll(pageable);

        return posts.map(p -> new PostResListDto(p.getId(), p.getTitle(), p.getAuthor() == null ? "익명" : p.getAuthor().getEmail()));
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

    public Page<PostResListDto> findByAppointment(Pageable pageable) {
        Page<Post> posts = postRepository.findByAppointment(null, pageable);

        return posts.map(p -> new PostResListDto(p.getId(), p.getTitle(), p.getAuthor() == null ? "익명" : p.getAuthor().getEmail()));
    }

    public void save(PostReqCreateDto postReqCreateDto) {
        Author author = authorRepository.findByEmail(postReqCreateDto.getEmail()).orElse(null);
        LocalDateTime localDateTime = null;
        String appointment = null;

        if(postReqCreateDto.getAppointment().equals("Y") && !postReqCreateDto.getAppointmentTime().isEmpty()){
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
            localDateTime = LocalDateTime.parse(postReqCreateDto.getAppointmentTime(), dateTimeFormatter);
            LocalDateTime now = LocalDateTime.now();
            if(localDateTime.isBefore(now)){
                throw new IllegalArgumentException("시간정보 잘못입력");
            }
            appointment = "Y";
        }
        Post post = Post.builder()
                .title(postReqCreateDto.getTitle())
                .contents(postReqCreateDto.getContents())
                .author(author)
                .appointment(appointment)
                .appointmentTime(localDateTime)
                .build();
        // 더티채킹 테스트
//        author.updateAuthor("dirty checking test", "1234");
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

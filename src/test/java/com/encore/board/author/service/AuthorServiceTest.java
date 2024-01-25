package com.encore.board.author.service;

import com.encore.board.domain.author.Role;
import com.encore.board.domain.author.domain.Author;
import com.encore.board.domain.author.dto.AuthorDetailResDto;
import com.encore.board.domain.author.dto.AuthorUpdateReqDto;
import com.encore.board.domain.author.repository.AuthorRepository;
import com.encore.board.domain.author.service.AuthorService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class AuthorServiceTest {
    @Autowired
    private AuthorService authorService;

    // 가짜 객체를 만드는 작업을 목킹이라 한다.
    @MockBean
    private AuthorRepository authorRepository;

    @Test
    void updateTest(){
        Long authorId = 1L;
        Author author = Author.builder()
                .name("test")
                .email("test@naver.com")
                .password("1234")
                .build();
        Mockito.when((authorRepository.findById(authorId))).thenReturn(Optional.of(author));

        AuthorUpdateReqDto authorUpdateReqDto = AuthorUpdateReqDto.builder()
                .id(1L)
                .name("test2")
                .password("4321")
                .build();
        authorService.authorUpdate(authorUpdateReqDto);

        Assertions.assertEquals(author.getName(), authorUpdateReqDto.getName());
        Assertions.assertEquals(author.getPassword(), authorUpdateReqDto.getPassword());
    }

    @Test
    void findAllTest(){
        // Mock repository 기능 구현
        List<Author> authors = new ArrayList<>();
        authors.add(new Author());
        authors.add(new Author());
        Mockito.when(authorRepository.findAll()).thenReturn(authors);

        // 검증
        Assertions.assertEquals(2, authorService.authorList().size());
    }

    @Test
    void findDetail(){
        Long authorId = 1L;
        Author author = Author.builder()
                .name("test")
                .email("test@naver.com")
                .password("1234")
                .posts(new ArrayList<>(5))
                .createdTime(LocalDateTime.now())
                .role(Role.ADMIN)
                .build();
        Mockito.when((authorRepository.findById(authorId))).thenReturn(Optional.of(author));

        AuthorDetailResDto authorDetailResDto = authorService.findAuthorDetail(authorId);

        Assertions.assertEquals(author.getName(), authorDetailResDto.getName());
        Assertions.assertEquals(author.getEmail(), authorDetailResDto.getEmail());
        Assertions.assertEquals(author.getPassword(), authorDetailResDto.getPassword());
        Assertions.assertEquals(author.getCreatedTime(), authorDetailResDto.getCreatedTime());
        Assertions.assertEquals(author.getPosts().size(), authorDetailResDto.getCount());
        Assertions.assertEquals("관리자", authorDetailResDto.getRole());

    }
}

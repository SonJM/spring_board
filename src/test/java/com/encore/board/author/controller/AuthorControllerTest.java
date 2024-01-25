package com.encore.board.author.controller;

import com.encore.board.domain.author.dto.AuthorDetailResDto;
import com.encore.board.domain.author.service.AuthorService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthorControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthorService authorService;

    @Test
    @WithMockUser // security 의존성 추가 필요
    void authorDetailTest() throws Exception {
        AuthorDetailResDto authorDetailResDto = AuthorDetailResDto.builder()
                .name("testname")
                .email("test@naver.com")
                .password("1234")
                .build();
        Mockito.when(authorService.findAuthorDetail(1L)).thenReturn(authorDetailResDto);
        mockMvc.perform(MockMvcRequestBuilders.get("/author/1/circle/dto"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect((ResultMatcher) jsonPath("$.name", authorDetailResDto.getName()));
    }
}

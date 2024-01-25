package com.encore.board.author.repository;

import com.encore.board.domain.author.Role;
import com.encore.board.domain.author.domain.Author;
import com.encore.board.domain.author.repository.AuthorRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


@DataJpaTest // 테스트가 종료되면 자동으로 DB 원상복구, 모든 스프링 빈을 생성하지 않고, DB 테스트 특화 어노테이션
// replace = @AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY) : H2DB(spring 내장 인메모리)가 기본설정
// @SpringBootTest // 자동 롤백 기능 지원 X, 실제 스프링 실행과 동일하게 스프링빈 생성 및 주입
// @Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AuthorRepositoryTest {
    @Autowired
    private AuthorRepository authorRepository;

    @Test
    public void authorSaveTest(){
        // 객체 생성 -> 재조회 -> 비교
        // 준비(prepare, given) 단계
        Author author = Author.builder()
                .email("test3@naver.com")
                .name("test")
                .password("1234")
                .role(Role.ADMIN)
                .build();
        // 실행(execute, when) 단계
        authorRepository.save(author);
        Author authorDb = authorRepository.findByEmail("test2@naver.com").orElse(null);

        // 검증(then) 단계
        // Assertions클래스의 기능을 통해 오휴의 원인파악. null처리, 가시적으로 성공/실패 여부 확인
        Assertions.assertEquals(author.getEmail(), authorDb.getEmail(), "is not same");
    }
}

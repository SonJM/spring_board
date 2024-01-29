package com.encore.board.domain.author.controller;

import com.encore.board.domain.author.service.AuthorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

// @Slf4j 어노테이션(롬복)을 통해 쉽게 logback 로그 라이브러리 사용가능
@Slf4j
@RestController
public class LogTestController {
//    @Slf4j 어노테이션 사용하지 않고, 직접 라이브러리 import하여 로거 생성가능
//    private static final Logger log = LoggerFactory.getLogger(LogTestController.class);
    private final AuthorService authorService;

    @Autowired
    public LogTestController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping("log/test1")
    public String testMethod1(){
        log.debug("디버그 로그입니다");
        log.info("info 로그다.");
        log.error("에러다!!에러!!삐융삐융");
        return "ok";
    }

    @GetMapping("exception/test1/{id}")
    public String exceptionTestMethod1(@PathVariable Long id){
        authorService.findById(id);
        return "ok";
    }
}

/*
package com.encore.board.domain.post.service;

import com.encore.board.domain.post.domain.Post;
import com.encore.board.domain.post.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class PostScheduler {
    private final PostRepository postRepository;

    @Autowired
    public PostScheduler(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    // 초 분 시간 일 월 요일 형태로 스케쥴링 설정
    // * : 매 초(분/시 동)을 의미
    // 특정숫자 : 특정숫자의 초(분/시 등)을 의미
    // 0/특정숫자 : 특정숫자마다
    // ex) 0 0 * * * * : 매일 0분 0초에 스케쥴링 시작
    // ex) 0 0/1 * * * * : 매일 1분마다 0초에 스케쥴링 시작
    @Scheduled(cron = "0 0/1 * * * *")
    public void postSchedule(){
        System.out.println("==== 스케쥴러 시작 ====");
        Page<Post> posts = postRepository.findByAppointment("Y", Pageable.unpaged());
        for(Post post : posts.getContent()){
            if(post.getAppointmentTime().isBefore(LocalDateTime.now())){
                post.UpdateAppointment(null);
                postRepository.save(post);
            }
        }
        System.out.println("==== 스케쥴러 끝 ====");
    }
}
*/

package com.encore.board.domain.post.repository;

import com.encore.board.domain.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByOrderByCreatedTimeDesc();

    // select p.* from post p left join author a on p.author_id = a.id;
    // 아래 jpql의 join문은 author객체를 통해 post를 스크리닝하고 싶은 상황에 적합
    @Query("select p from Post p left join p.author") // jpql문
    List<Post> findAllJoin();
    // select p.*, a.* from post p left join author a on p.author_id = a.id;
    @Query("select p from Post p left join fetch p.author")
    List<Post> findAllFetchJoin();
}

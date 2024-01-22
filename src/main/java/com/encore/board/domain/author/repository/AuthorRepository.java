package com.encore.board.domain.author.repository;

import com.encore.board.domain.author.domain.Author;
import com.encore.board.domain.author.dto.AuthorListResDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
}

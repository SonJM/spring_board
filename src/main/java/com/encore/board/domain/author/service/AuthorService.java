package com.encore.board.domain.author.service;

import com.encore.board.domain.author.domain.Author;
import com.encore.board.domain.author.dto.AuthorDetailResDto;
import com.encore.board.domain.author.dto.AuthorListResDto;
import com.encore.board.domain.author.dto.AuthorSaveReqDto;
import com.encore.board.domain.author.repository.AuthorRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional
public class AuthorService {
    private final AuthorRepository authorRepository;
    @Autowired
    public AuthorService(AuthorRepository authorRepository) {this.authorRepository = authorRepository;}

    public void save(AuthorSaveReqDto authorSaveReqDto){
        Author author = new Author(
                authorSaveReqDto.getName(),
                authorSaveReqDto.getEmail(),
                authorSaveReqDto.getPassword()
        );
        authorRepository.save(author);
    }
    public List<AuthorListResDto> authorList(){
        List<Author> authorList = authorRepository.findAll();
        List<AuthorListResDto> authorListResDtos = new ArrayList<>();
        for(Author author : authorList){
            AuthorListResDto authorListResDto = new AuthorListResDto(
                    author.getId(),
                    author.getName(),
                    author.getEmail()
            );
            authorListResDtos.add(authorListResDto);
            log.info(authorListResDto.toString());
        }
        return authorListResDtos;
    }

    public AuthorDetailResDto authorDetail(Long id) throws EntityNotFoundException{
        Author author = authorRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        return new AuthorDetailResDto(
                author.getId(),
                author.getName(),
                author.getEmail(),
                author.getPassword(),
                author.getCreatedTime()
        );
    }
}

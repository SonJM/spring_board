package com.encore.board.domain.author.service;

import com.encore.board.domain.author.Role;
import com.encore.board.domain.author.domain.Author;
import com.encore.board.domain.author.dto.AuthorDetailResDto;
import com.encore.board.domain.author.dto.AuthorListResDto;
import com.encore.board.domain.author.dto.AuthorSaveReqDto;
import com.encore.board.domain.author.dto.AuthorUpdateReqDto;
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
        Role role = null;
        if(authorSaveReqDto.getRole() == null || authorSaveReqDto.getRole().equals("user")){
            role = Role.USER;
        } else role = Role.ADMIN;
        // 후보테이블을 통해 시작테이블에
        Author author = Author.builder()
                .email(authorSaveReqDto.getEmail())
                .name(authorSaveReqDto.getName())
                .password(authorSaveReqDto.getPassword())
                .role(role)
                .build();

        /*List<Post> posts = new ArrayList<>();
        Post post = Post.builder()
                .title("오늘 점심 메뉴는 " + author.getName()+ "입니다.")
                .contents("배고픕니다")
                .author(author)
                .build();
        posts.add(post);
        author.setPosts(posts);*/
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

    public AuthorDetailResDto findById(Long id) throws EntityNotFoundException{
        Author author = authorRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        String role = "";
        if(author.getRole() == null || author.getRole().equals(Role.USER)){
            role = "유저";
        } else role = "관리자";
        return AuthorDetailResDto.builder()
                .id(author.getId())
                .name(author.getName())
                .email(author.getEmail())
                .count(author.getPosts().size())
                .createdTime(author.getCreatedTime())
                .password(author.getPassword())
                .role(role)
                .build();
    }

    public void delete(Long id) throws EntityNotFoundException{
        Author author = authorRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        authorRepository.delete(author);
    }

    public void authorUpdate(AuthorUpdateReqDto authorUpdateReqDto) {
        Author author = authorRepository.findById(authorUpdateReqDto.getId()).orElseThrow(EntityNotFoundException::new);
        author.setName(authorUpdateReqDto.getName());
        author.setPassword(authorUpdateReqDto.getPassword());
        // 명시적으로 save를 하지 않더라도, jpa의 영속성컨텍스트를 통해,
        // 객체에 변경이 감지(더티 체킹)되면, 트랜잭션이 완료되는 시점에 save 동작.
        authorRepository.save(author);
    }
}

package com.encore.board.domain.author.controller;

import com.encore.board.domain.author.domain.Author;
import com.encore.board.domain.author.dto.AuthorDetailResDto;
import com.encore.board.domain.author.dto.AuthorSaveReqDto;
import com.encore.board.domain.author.dto.AuthorUpdateReqDto;
import com.encore.board.domain.author.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AuthorController {
    private final AuthorService authorService;
    @Autowired
    public AuthorController(AuthorService authorService) {this.authorService = authorService;}

    @GetMapping("/author/create")
    public String authorCreate(){
        return "author/author-create";
    }

    @PostMapping("/author/create")
    public String authorSave(AuthorSaveReqDto authorSaveReqDto, Model model){
        try {
            authorService.save(authorSaveReqDto);
            return "redirect:/author/list";
        } catch(IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "redirect:/author/create";
        }
    }

    @GetMapping("/author/list")
    public String authorList(Model model){
        model.addAttribute("authorList", authorService.authorList());
        return "author/author-list";
    }

    @GetMapping("/author/detail/{id}")
    public String authorDetail(@PathVariable Long id, Model model)
    {
        model.addAttribute("author", authorService.findAuthorDetail(id));
        return "author/author-detail";
    }

    @PostMapping("/author/update")
    public String authorUpdate(AuthorUpdateReqDto authorUpdateReqDto){
        authorService.authorUpdate(authorUpdateReqDto);
        return "redirect:/author/detail/" + authorUpdateReqDto.getId();
    }

    @GetMapping("author/delete/{id}")
    public String authorDelete(@PathVariable Long id){
        authorService.delete(id);
        return "redirect:/author/list";
    }

    @GetMapping("/author/{id}/circle/entity")
    @ResponseBody
    // 연관관계가 있는 Author엔티티를 json으로 직렬화를 하게 될 경우
    // 순환참조 이슈 발생하므로, dto 사용필요
    public Author circleEntityTest(@PathVariable Long id){
        return authorService.findById(id);
    }

    @GetMapping("/author/{id}/circle/dto")
    @ResponseBody
    public AuthorDetailResDto circleDtoTest(@PathVariable Long id){
        return authorService.findAuthorDetail(id);
    }
}

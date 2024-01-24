package com.encore.board.domain.author.controller;

import com.encore.board.domain.author.dto.AuthorSaveReqDto;
import com.encore.board.domain.author.dto.AuthorUpdateReqDto;
import com.encore.board.domain.author.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

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
    public String authorSave(AuthorSaveReqDto authorSaveReqDto){
        authorService.save(authorSaveReqDto);
        return "redirect:/author/list";
    }

    @GetMapping("/author/list")
    public String authorList(Model model){
        model.addAttribute("authorList", authorService.authorList());
        return "author/author-list";
    }

    @GetMapping("/author/detail/{id}")
    public String authorDetail(@PathVariable Long id, Model model)
    {
        model.addAttribute("author", authorService.findById(id));
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
}

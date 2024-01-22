package com.encore.board.domain.author.controller;

import com.encore.board.domain.author.dto.AuthorSaveReqDto;
import com.encore.board.domain.author.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class AuthorController {
    private final AuthorService authorService;
    @Autowired
    public AuthorController(AuthorService authorService) {this.authorService = authorService;}

    @PostMapping("/author/save")
    @ResponseBody
    public String authorSave(@Valid @RequestBody AuthorSaveReqDto authorSaveReqDto){
        authorService.save(authorSaveReqDto);
        return "ok";
    }

    @GetMapping("/author/list")
    public String authorList(Model model){
        model.addAttribute("authorList", authorService.authorList());
        return "author/author-list";
    }

    @GetMapping("author/detail/{id}")
    public String authorDetail(@PathVariable Long id, Model model)
    {
        model.addAttribute("author", authorService.authorDetail(id));
        return "author/author-detail";
    }
}

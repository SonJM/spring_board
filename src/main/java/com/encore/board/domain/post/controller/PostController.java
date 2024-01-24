package com.encore.board.domain.post.controller;

import com.encore.board.domain.post.dto.PostReqCreateDto;
import com.encore.board.domain.post.dto.PostReqUpdateDto;
import com.encore.board.domain.post.dto.PostResListDto;
import com.encore.board.domain.post.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Slf4j
@Controller
public class PostController {
    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {this.postService = postService;}

    @GetMapping("/post/list")
    public String postList(Model model){
        List<PostResListDto> listDtos = postService.getPostList();
        model.addAttribute("postList", listDtos);
        return "post/post-list";
    }

    @PostMapping("post/{id}/update")
    public String postUpdate(@PathVariable Long id, PostReqUpdateDto postReqUpdateDto){
        postService.postUpdate(id, postReqUpdateDto);
        return "redirect:/post/list";
    }

    @GetMapping("/post/detail/{id}")
    public String postDetail(@PathVariable Long id, Model model){
        model.addAttribute("post", postService.findById(id));
        return "post/post-detail";
    }

    @GetMapping("/post/create")
    public String postWrite(){
        return "post/post-create";
    }

    @PostMapping("/post/create")
    public String postWrite(PostReqCreateDto postReqCreateDto){
        postService.postWrite(postReqCreateDto);
        return "redirect:/post/list";
    }

    @GetMapping("/post/delete/{id}")
    public String postDelete(@PathVariable Long id){
        postService.delete(id);
        return "redirect:/post/list";
    }
}

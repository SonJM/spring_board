package com.encore.board.domain.post.controller;

import com.encore.board.domain.post.dto.PostReqCreateDto;
import com.encore.board.domain.post.dto.PostReqUpdateDto;
import com.encore.board.domain.post.dto.PostResListDto;
import com.encore.board.domain.post.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
public class PostController {
    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {this.postService = postService;}

    @GetMapping("/post/list")
    @CrossOrigin(originPatterns = "*")
    // localhost:8080/post/list?size=xx&page=xx&sort=xx,desc
    public String postList(Model model, @PageableDefault(size = 5, sort = "createdTime", direction = Sort.Direction.DESC) Pageable pageable){
        Page<PostResListDto> listDtos = postService.findByAppointment(pageable);
        model.addAttribute("postList", listDtos);
        return "post/post-list";
    }

    @PostMapping("post/{id}/update")
    @CrossOrigin(originPatterns = "*")
    public String postUpdate(@PathVariable Long id, PostReqUpdateDto postReqUpdateDto){
        postService.postUpdate(id, postReqUpdateDto);
        return "redirect:/post/list";
    }

    @GetMapping("/post/detail/{id}")
    @CrossOrigin(originPatterns = "*")
    public String postDetail(@PathVariable Long id, Model model){
        model.addAttribute("post", postService.findById(id));
        return "post/post-detail";
    }

    @GetMapping("/post/create")
    @CrossOrigin(originPatterns = "*")
    public String postWrite(){
        return "post/post-create";
    }

    @PostMapping("/post/create")
    @CrossOrigin(originPatterns = "*")
    public String postWrite(Model model, PostReqCreateDto postReqCreateDto){
        try {
            postService.save(postReqCreateDto);
            return "redirect:/post/list";
        } catch(IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "post/post-create";
        }
    }

    @GetMapping("/post/delete/{id}")
    @CrossOrigin(originPatterns = "*")
    public String postDelete(@PathVariable Long id){
        postService.delete(id);
        return "redirect:/post/list";
    }
}

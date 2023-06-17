package com.practice.board.domain.presentation.controller;

import com.practice.board.domain.service.like.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/like")
@RequiredArgsConstructor
@RestController
public class LikeController {

    private final LikeService likeService;

    @PostMapping("/{boardId}")
    public void likeAdd(@PathVariable Long boardId){
        likeService.addLike(boardId);
    }

    @DeleteMapping("/{boardId}")
    public void likeRemove(@PathVariable Long boardId){
        likeService.removeLike(boardId);
    }

}

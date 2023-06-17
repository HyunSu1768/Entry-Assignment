package com.practice.board.domain.presentation.controller;

import com.practice.board.domain.presentation.dto.request.CommentRequest;
import com.practice.board.domain.service.comment.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/comment")
@RequiredArgsConstructor
@RestController
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/{boardId}")
    public void commentAdd(@PathVariable Long boardId, @RequestBody CommentRequest request) {
        commentService.addComment(request, boardId);
    }

    @PutMapping("/{commentId}")
    public void commentModify(@PathVariable Long commentId, @RequestBody CommentRequest request) {
        commentService.modifyComment(request, commentId);
    }

    @DeleteMapping("/{commentId}")
    public void commentRemove(@PathVariable Long commentId){
        commentService.removeComment(commentId);
    }
}

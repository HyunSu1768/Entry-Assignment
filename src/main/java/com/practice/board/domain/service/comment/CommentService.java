package com.practice.board.domain.service.comment;

import com.practice.board.domain.presentation.dto.request.CommentRequest;

public interface CommentService {

    void addComment(CommentRequest request, Long boardId);

    void modifyComment(CommentRequest commentRequest, Long commentId);

    void removeComment(Long commentId);

}

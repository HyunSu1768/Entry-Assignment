package com.practice.board.domain.service.exception.comment;

import com.practice.board.global.error.exception.BusinessException;
import com.practice.board.global.error.exception.ErrorCode;

public class CommentNotFoundException extends BusinessException {

    public static final BusinessException EXCEPTION = new CommentNotFoundException();

    public CommentNotFoundException() {
        super(ErrorCode.COMMENT_NOT_FOUND);
    }
}

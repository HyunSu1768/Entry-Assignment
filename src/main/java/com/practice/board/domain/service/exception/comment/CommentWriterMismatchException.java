package com.practice.board.domain.service.exception.comment;

import com.practice.board.global.error.exception.BusinessException;
import com.practice.board.global.error.exception.ErrorCode;

public class CommentWriterMismatchException extends BusinessException {

    public static final BusinessException EXCEPTION = new CommentWriterMismatchException();

    public CommentWriterMismatchException() {
        super(ErrorCode.COMMENT_WRITER_MISMATCH);
    }
}

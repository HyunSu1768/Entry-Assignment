package com.practice.board.domain.service.exception.like;

import com.practice.board.global.error.exception.BusinessException;
import com.practice.board.global.error.exception.ErrorCode;

public class LikeDoesntExistsException extends BusinessException {

    public static final BusinessException EXCEPTION = new LikeDoesntExistsException();

    public LikeDoesntExistsException() {
        super(ErrorCode.LIKE_NOT_FOUND);
    }
}

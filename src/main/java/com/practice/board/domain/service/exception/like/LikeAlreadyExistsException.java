package com.practice.board.domain.service.exception.like;

import com.practice.board.global.error.exception.BusinessException;
import com.practice.board.global.error.exception.ErrorCode;

public class LikeAlreadyExistsException extends BusinessException {

    public static final BusinessException EXCEPTION = new LikeAlreadyExistsException();

    public LikeAlreadyExistsException() {
        super(ErrorCode.LIKE_ALREADY_EXISTS);
    }
}

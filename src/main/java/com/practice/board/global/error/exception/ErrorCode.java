package com.practice.board.global.error.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ErrorCode {

    // jwt
    EXPIRED_TOKEN(401 , "Expired token"),
    INVALID_TOKEN(401, "Invalid token"),

    // user
    USER_NOT_FOUND(404,"User not found"),
    USER_ALREADY_EXISTS(409, "User already exists"),
    PASSWORD_MISMATCH(404, "Password mismatch"),

    // board
    BOARD_NOT_FOUND(404, "Board not found"),
    BOARD_WRITER_MISMATCH(403, "Board writer mismatch"),

    // comment
    COMMENT_NOT_FOUND(404, "Comment not found"),
    COMMENT_WRITER_MISMATCH(403, "Comment Writer Mismatch"),

    // like
    LIKE_ALREADY_EXISTS(400, "Like already exists"),
    LIKE_NOT_FOUND(400, "Like not found"),

    // general
    BAD_REQUEST(400, "Bad request"),
    INTERNAL_SERVER_ERROR(500, "Internal server error");

    private final int statusCode;
    private final String message;
}
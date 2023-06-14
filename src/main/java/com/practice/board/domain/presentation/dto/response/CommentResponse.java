package com.practice.board.domain.presentation.dto.response;

import com.practice.board.domain.persistence.commnet.Comment;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CommentResponse {

    private Long commentId;

    private String content;

    private String writer;

    public static CommentResponse of(Comment comment){

        return CommentResponse.builder()
                .commentId(comment.getId())
                .writer(comment.getUser().getUsername())
                .content(comment.getContent())
                .build();
    }
}

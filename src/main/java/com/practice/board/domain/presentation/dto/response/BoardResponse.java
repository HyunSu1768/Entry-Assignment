package com.practice.board.domain.presentation.dto.response;

import com.practice.board.domain.persistence.board.Board;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class BoardResponse {

    private Long id;

    private String username;

    private String title;

    private String content;

    private int viewCount;

    private int commentCount;

    private int likeCount;

    private LocalDateTime createAt;

    private LocalDateTime updateAt;

    private List<CommentResponse> commentResponseList;

    private List<LikeResponse> likeResponseList;


    public static BoardResponse of(Board board) {

        List<CommentResponse> commentList = board.getCommentList().stream()
                .map(CommentResponse::of)
                .toList();

        List<LikeResponse> likeList = board.getLikeList()
                .stream()
                .map(LikeResponse::of)
                .toList();

        return BoardResponse.builder()
                .createAt(board.getCreateAt())
                .updateAt(board.getUpdateAt())
                .likeCount(likeList.size())
                .likeResponseList(likeList)
                .commentCount(commentList.size())
                .viewCount(board.getViewCount())
                .id(board.getId())
                .username(board.getUser().getUsername())
                .title(board.getTitle())
                .content(board.getContent())
                .commentResponseList(commentList)
                .build();
    }
}
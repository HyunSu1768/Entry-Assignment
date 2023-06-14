package com.practice.board.domain.presentation.dto.response;

import com.practice.board.domain.service.like.Like;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class LikeResponse {

    private String username;

    public static LikeResponse of(Like like){
        return LikeResponse.builder()
                .username(like.getUser().getUsername())
                .build();
    }

}

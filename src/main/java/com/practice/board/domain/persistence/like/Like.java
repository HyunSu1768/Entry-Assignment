package com.practice.board.domain.persistence.like;

import com.practice.board.domain.persistence.board.Board;
import com.practice.board.domain.persistence.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@IdClass(LikeId.class)
@Entity(name = "tbl_like")
public class Like {

    @Id
    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    @Id
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}

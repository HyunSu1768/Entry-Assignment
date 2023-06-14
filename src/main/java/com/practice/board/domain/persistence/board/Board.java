package com.practice.board.domain.persistence.board;

import com.practice.board.domain.persistence.commnet.Comment;
import com.practice.board.domain.service.like.Like;
import com.practice.board.domain.persistence.user.User;
import com.practice.board.global.common.entity.BaseTimeEntity;
import lombok.*;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity(name = "tbl_board")
public class Board extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writer_id", nullable = false)
    private User user;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE)
    private List<Comment> commentList = new ArrayList<>();

    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE)
    private List<Like> likeList = new ArrayList<>();

    @Column(name = "view_count")
    private int viewCount = 0;

    public void modifyTitleAndContent(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void addViewCount(){
        this.viewCount++;
    }
}
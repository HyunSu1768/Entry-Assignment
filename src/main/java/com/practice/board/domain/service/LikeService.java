package com.practice.board.domain.service;

import com.practice.board.domain.persistence.board.Board;
import com.practice.board.domain.persistence.board.BoardRepository;
import com.practice.board.domain.service.like.Like;
import com.practice.board.domain.service.like.LikeRepository;
import com.practice.board.domain.persistence.user.User;
import com.practice.board.domain.service.exception.board.BoardNotFoundException;
import com.practice.board.domain.service.exception.like.LikeAlreadyExistsException;
import com.practice.board.domain.service.exception.like.LikeDoesntExistsException;
import com.practice.board.domain.service.facade.UserFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
@Service
public class LikeService {

    private final LikeRepository likeRepository;

    private final BoardRepository boardRepository;

    private final UserFacade userFacade;

    public void addLike(Long boardId){

        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> BoardNotFoundException.EXCEPTION);

        User curUser = userFacade.currentUser();

        if(likeRepository.existsByUserAndBoard(curUser, board)){
            throw LikeAlreadyExistsException.EXCEPTION;
        }

        likeRepository.save(Like.builder()
                        .user(curUser)
                        .board(board)
                        .build());
    }

    public void removeLike(Long boardId) {

        Board board = boardRepository.findById(boardId)
                .orElseThrow(()->BoardNotFoundException.EXCEPTION);

        User curUser = userFacade.currentUser();

        if(!likeRepository.existsByUserAndBoard(curUser, board)){
            throw LikeDoesntExistsException.EXCEPTION;
        }

        Like like = likeRepository.findByUserAndBoard(curUser, board)
                .orElseThrow(()->LikeDoesntExistsException.EXCEPTION);

        likeRepository.delete(like);

    }
}

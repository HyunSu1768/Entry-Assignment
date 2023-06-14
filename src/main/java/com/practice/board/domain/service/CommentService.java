package com.practice.board.domain.service;

import com.practice.board.domain.persistence.board.Board;
import com.practice.board.domain.persistence.board.BoardRepository;
import com.practice.board.domain.persistence.commnet.Comment;
import com.practice.board.domain.persistence.commnet.CommentRepository;
import com.practice.board.domain.persistence.user.User;
import com.practice.board.domain.presentation.dto.request.CommentRequest;
import com.practice.board.domain.service.exception.board.BoardNotFoundException;
import com.practice.board.domain.service.exception.comment.CommentNotFoundException;
import com.practice.board.domain.service.exception.comment.CommentWriterMismatchException;
import com.practice.board.domain.service.facade.UserFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class CommentService {

    private final CommentRepository commentRepository;

    private final BoardRepository boardRepository;

    private final UserFacade userFacade;

    @Transactional
    public void addComment(
            CommentRequest request,
            Long boardId
    ){

        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> BoardNotFoundException.EXCEPTION);

        commentRepository.save(Comment.builder()
                .content(request.getContent())
                .board(board)
                .user(userFacade.currentUser())
                .build());

    }

    @Transactional
    public void modifyComment(
            CommentRequest commentRequest,
            Long commentId
    ){
        User curUser = userFacade.currentUser();

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(()-> CommentNotFoundException.EXCEPTION);

        if(!comment.getUser().equals(curUser)){
            throw CommentWriterMismatchException.EXCEPTION;
        }
        comment.updateComment(commentRequest.getContent());
    }

    @Transactional
    public void removeComment(Long commentId){
        User curUser = userFacade.currentUser();

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(()->CommentNotFoundException.EXCEPTION);

        if(!comment.getUser().equals(curUser)){
            throw CommentWriterMismatchException.EXCEPTION;
        }
        commentRepository.delete(comment);
    }

}

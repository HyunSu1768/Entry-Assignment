package com.practice.board.domain.service.comment;

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
@Transactional
@Service
public class CommentServiceImpl implements CommentService{

    private final CommentRepository commentRepository;

    private final BoardRepository boardRepository;

    private final UserFacade userFacade;

    @Override
    public void addComment(CommentRequest request, Long boardId){

        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> BoardNotFoundException.EXCEPTION);

        commentRepository.save(Comment.builder()
                .content(request.getContent())
                .board(board)
                .user(userFacade.currentUser())
                .build());

    }

    @Override
    public void modifyComment(CommentRequest commentRequest, Long commentId){
        User curUser = userFacade.currentUser();

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(()-> CommentNotFoundException.EXCEPTION);

        if(!comment.getUser().equals(curUser)){
            throw CommentWriterMismatchException.EXCEPTION;
        }
        comment.updateComment(commentRequest.getContent());
    }

    @Override
    public void removeComment(Long commentId){
        User user = userFacade.currentUser();

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(()->CommentNotFoundException.EXCEPTION);

        if(!comment.getUser().equals(user)){
            throw CommentWriterMismatchException.EXCEPTION;
        }
        commentRepository.delete(comment);
    }

}

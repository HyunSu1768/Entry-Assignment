package com.practice.board.domain.service.board;

import com.practice.board.domain.presentation.dto.request.BoardRequest;
import com.practice.board.domain.presentation.dto.response.BoardIdResponse;
import com.practice.board.domain.presentation.dto.response.BoardResponse;
import com.practice.board.domain.persistence.board.Board;
import com.practice.board.domain.persistence.board.BoardRepository;
import com.practice.board.domain.persistence.user.User;
import com.practice.board.domain.service.facade.UserFacade;
import com.practice.board.domain.service.exception.board.BoardNotFoundException;
import com.practice.board.domain.service.exception.board.BoardWriterMismatchException;
import lombok.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Transactional
@RequiredArgsConstructor
@Service
public class BoardServiceImpl implements BoardService{

    private final BoardRepository boardRepository;
    private final UserFacade userFacade;

    @Override
    public BoardIdResponse writeBoard(BoardRequest request) {

        User currentUser = userFacade.currentUser();

        Board board = boardRepository.save(
                Board.builder()
                        .user(currentUser)
                        .title(request.getTitle())
                        .content(request.getContent())
                        .build()
        );
        return new BoardIdResponse(board.getId());
    }

    @Override
    public void modifyBoard(Long boardId, BoardRequest request) {

        User currentUser = userFacade.currentUser();
        Board board = boardRepository.findById(boardId)
                        .orElseThrow(() -> BoardNotFoundException.EXCEPTION);

        writerCheck(currentUser, board);

        board.modifyTitleAndContent(request.getTitle(), request.getContent());
        boardRepository.save(board);
    }

    @Override
    public void deleteBoard(Long boardId) {

        User currentUser = userFacade.currentUser();
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> BoardNotFoundException.EXCEPTION);

        writerCheck(currentUser, board);

        boardRepository.deleteById(boardId);
    }

    private void writerCheck(User user, Board board) {
        if (board.getUser() != user) {
            throw BoardWriterMismatchException.EXCEPTION;
        }
    }

    @Override
    public BoardResponse findBoard(Long boardId) {

        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> BoardNotFoundException.EXCEPTION);

        board.addViewCount();

        return BoardResponse.of(board);
    }

    @Override
    public List<BoardResponse> findAllBoards() {

        return boardRepository.findAll()
                .stream()
                .map(BoardResponse::of)
                .collect(Collectors.toList());
    }

    @Override
    public List<BoardResponse> findMyBoards() {

        User currentUser = userFacade.currentUser();

        return boardRepository.findByUserId(currentUser.getId())
                .stream()
                .map(BoardResponse::of)
                .collect(Collectors.toList());
    }

    @Override
    public List<BoardResponse> searchBoard(String title) {
        return boardRepository.findByTitleContaining(title)
                .stream()
                .map(BoardResponse::of)
                .collect(Collectors.toList());
    }
}
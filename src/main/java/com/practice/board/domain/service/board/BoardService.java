package com.practice.board.domain.service.board;

import com.practice.board.domain.persistence.board.Board;
import com.practice.board.domain. persistence.user.User;
import com.practice.board.domain.presentation.dto.request.BoardRequest;
import com.practice.board.domain.presentation.dto.response.BoardIdResponse;
import com.practice.board.domain.presentation.dto.response.BoardResponse;

import java.util.List;

public interface BoardService {

    BoardIdResponse writeBoard(BoardRequest request);

    void modifyBoard(Long boardId, BoardRequest request);

    void deleteBoard(Long boardId);

    BoardResponse findBoard(Long boardId);

    List<BoardResponse> findAllBoards();

    List<BoardResponse> findMyBoards();

    List<BoardResponse> searchBoard(String title);

}

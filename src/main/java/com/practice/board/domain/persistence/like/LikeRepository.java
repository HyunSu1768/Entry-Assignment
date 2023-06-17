package com.practice.board.domain.persistence.like;

import com.practice.board.domain.persistence.board.Board;
import com.practice.board.domain.persistence.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {

    boolean existsByUserAndBoard(User user, Board board);

    Optional<Like> findByUserAndBoard(User user, Board board);
}

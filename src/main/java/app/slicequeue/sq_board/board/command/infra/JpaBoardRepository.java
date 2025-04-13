package app.slicequeue.sq_board.board.command.infra;

import app.slicequeue.sq_board.board.command.domain.Board;
import app.slicequeue.sq_board.board.command.domain.BoardId;
import app.slicequeue.sq_board.board.command.domain.BoardRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JpaBoardRepository extends BoardRepository, JpaRepository<Board, BoardId> {

    @Override
    Optional<Board> findByBoardId(BoardId id);
}

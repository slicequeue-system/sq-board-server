package app.slicequeue.sq_board.board.command.domain;

import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BoardRepository {

    Board save(Board board);

    Optional<Board> findByBoardId(BoardId id);

}

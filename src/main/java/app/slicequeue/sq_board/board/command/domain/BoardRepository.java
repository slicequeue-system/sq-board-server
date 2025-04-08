package app.slicequeue.sq_board.board.command.domain;

import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository {

    Board save(Board board);

}

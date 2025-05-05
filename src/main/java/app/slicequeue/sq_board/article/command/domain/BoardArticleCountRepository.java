package app.slicequeue.sq_board.article.command.domain;

import app.slicequeue.sq_board.board.command.domain.BoardId;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BoardArticleCountRepository {

    BoardArticleCount save(BoardArticleCount boardArticleCount);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<BoardArticleCount> findLockedByBoardId(BoardId boardId);
}

package app.slicequeue.sq_board.article.command.infra;

import app.slicequeue.sq_board.article.command.domain.BoardArticleCount;
import app.slicequeue.sq_board.article.command.domain.BoardArticleCountRepository;
import app.slicequeue.sq_board.board.command.domain.BoardId;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JpaBoardArticleCountRepository
        extends BoardArticleCountRepository, JpaRepository<BoardArticleCount, BoardId> {

    BoardArticleCount save(BoardArticleCount boardArticleCount);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<BoardArticleCount> findLockedByBoardId(BoardId boardId);
}

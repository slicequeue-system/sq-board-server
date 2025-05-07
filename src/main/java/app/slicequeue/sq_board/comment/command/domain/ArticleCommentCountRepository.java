package app.slicequeue.sq_board.comment.command.domain;

import app.slicequeue.sq_board.article.command.domain.ArticleId;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ArticleCommentCountRepository {

    ArticleCommentCount save(ArticleCommentCount articleCommentCount);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<ArticleCommentCount> findLockedByArticleId(ArticleId articleId);
}

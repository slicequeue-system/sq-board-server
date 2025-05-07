package app.slicequeue.sq_board.comment.command.infra;

import app.slicequeue.sq_board.article.command.domain.ArticleId;
import app.slicequeue.sq_board.comment.command.domain.ArticleCommentCount;
import app.slicequeue.sq_board.comment.command.domain.ArticleCommentCountRepository;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JpaArticleCommentCountRepository extends ArticleCommentCountRepository,
        JpaRepository<ArticleCommentCount, ArticleId> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<ArticleCommentCount> findLockedByArticleId(ArticleId articleId);
}

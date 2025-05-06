package app.slicequeue.sq_board.article_reaction.command.infra;

import app.slicequeue.sq_board.article.command.domain.ArticleId;
import app.slicequeue.sq_board.article_reaction.command.domain.*;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JpaArticleReactionCountRepository extends
        ArticleReactionCountRepository, JpaRepository<ArticleReactionCount, ArticleReactionCountId> {

    ArticleReactionCount save(ArticleReactionCount articleReactionCount);

    void delete(ArticleReactionCount articleReactionCount);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<ArticleReactionCount> findLockedByArticleReactionCountId(ArticleReactionCountId articleReactionCountId);
}

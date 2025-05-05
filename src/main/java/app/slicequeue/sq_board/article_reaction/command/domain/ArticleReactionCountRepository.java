package app.slicequeue.sq_board.article_reaction.command.domain;

import app.slicequeue.sq_board.article.command.domain.ArticleId;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ArticleReactionCountRepository {

    ArticleReactionCount save(ArticleReactionCount articleReactionCount);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<ArticleReactionCount> findLockedByArticleIdAndEmoji(ArticleId articleId, String emoji);
}

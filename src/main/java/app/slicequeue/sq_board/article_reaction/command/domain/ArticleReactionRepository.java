package app.slicequeue.sq_board.article_reaction.command.domain;

import app.slicequeue.sq_board.article.command.domain.ArticleId;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ArticleReactionRepository {

    ArticleReaction save(ArticleReaction articleReaction);
    void delete(ArticleReaction articleReaction);
    Optional<ArticleReaction> findByArticleIdAndEmojiAndUserId(ArticleId articleId, String emoji, Long userId);
}

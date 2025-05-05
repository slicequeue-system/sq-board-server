package app.slicequeue.sq_board.article_reaction.command.infra;

import app.slicequeue.sq_board.article.command.domain.ArticleId;
import app.slicequeue.sq_board.article_reaction.command.domain.ArticleReaction;
import app.slicequeue.sq_board.article_reaction.command.domain.ArticleReactionId;
import app.slicequeue.sq_board.article_reaction.command.domain.ArticleReactionRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JpaArticleReactionRepository extends ArticleReactionRepository, JpaRepository<ArticleReaction, ArticleReactionId> {

    ArticleReaction save(ArticleReaction articleReaction);
    void delete(ArticleReaction articleReaction);
    Optional<ArticleReaction> findByArticleIdAndEmojiAndUserId(ArticleId articleId, String emoji, Long userId);
}

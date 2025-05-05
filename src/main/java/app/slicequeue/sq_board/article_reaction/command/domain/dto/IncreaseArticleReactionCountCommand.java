package app.slicequeue.sq_board.article_reaction.command.domain.dto;

import app.slicequeue.sq_board.article.command.domain.ArticleId;
import app.slicequeue.sq_board.article_reaction.command.domain.ArticleReaction;

public record IncreaseArticleReactionCountCommand(ArticleId articleId, String emoji) {

    public static IncreaseArticleReactionCountCommand from(CreateArticleReactionCommand command) {
        return new IncreaseArticleReactionCountCommand(command.articleId(), command.emoji());
    }
}

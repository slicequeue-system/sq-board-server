package app.slicequeue.sq_board.article_reaction.command.domain.dto;

import app.slicequeue.sq_board.article.command.domain.ArticleId;

public record ArticleReactionCountCommand(ArticleId articleId, String emoji) {

    public static ArticleReactionCountCommand from(ArticleReactionCommand command) {
        return new ArticleReactionCountCommand(command.articleId(), command.emoji());
    }
}

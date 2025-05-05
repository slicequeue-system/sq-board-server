package app.slicequeue.sq_board.article_reaction.command.domain.dto;

import app.slicequeue.sq_board.article.command.domain.ArticleId;
import app.slicequeue.sq_board.article_reaction.command.presentation.dto.ToggleArticleReactionRequest;

public record ArticleReactionCommand(ArticleId articleId, Long userId, String emoji) {

    public static ArticleReactionCommand from(ToggleArticleReactionRequest request) {
        return new ArticleReactionCommand(
                ArticleId.from(request.getArticleId()),
                request.getUserId(),
                request.getEmoji()
        );
    }
}

package app.slicequeue.sq_board.article_reaction.command.domain.dto;

import app.slicequeue.sq_board.article.command.domain.ArticleId;
import app.slicequeue.sq_board.article_reaction.command.presentation.dto.CreateArticleReactionRequest;
import lombok.Getter;

public record CreateArticleReactionCommand(ArticleId articleId, Long userId, String emoji) {

    public static CreateArticleReactionCommand from(CreateArticleReactionRequest request) {
        return new CreateArticleReactionCommand(
                ArticleId.from(request.getArticleId()),
                request.getUserId(),
                request.getEmoji()
        );
    }
}

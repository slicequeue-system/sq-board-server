package app.slicequeue.sq_board.article_view.command.domain.dto;

import app.slicequeue.sq_board.article.command.domain.ArticleId;
import app.slicequeue.sq_board.article_view.command.presentation.dto.IncreaseArticleViewCountRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

public record IncreaseArticleViewCountCommand(ArticleId articleId, long userId) {

    public static IncreaseArticleViewCountCommand from(IncreaseArticleViewCountRequest request) {
        return new IncreaseArticleViewCountCommand(ArticleId.from(request.getArticleId()), request.getUserId());
    }
}

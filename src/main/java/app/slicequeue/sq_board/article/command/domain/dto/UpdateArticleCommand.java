package app.slicequeue.sq_board.article.command.domain.dto;

import app.slicequeue.sq_board.article.command.domain.ArticleId;
import app.slicequeue.sq_board.article.command.presentation.dto.UpdateArticleRequest;
import lombok.Builder;

import java.util.List;

@Builder
public record UpdateArticleCommand(
        ArticleId articleId,
        String title,
        String content,
        List<String> tags) {

    public static UpdateArticleCommand from(ArticleId articleId, UpdateArticleRequest request) {

        return new UpdateArticleCommand(
                articleId,
                request.getTitle(),
                request.getContent(),
                request.getTags());
    }
}

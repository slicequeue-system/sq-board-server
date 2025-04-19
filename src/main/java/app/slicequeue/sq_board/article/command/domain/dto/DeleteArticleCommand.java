package app.slicequeue.sq_board.article.command.domain.dto;

import app.slicequeue.sq_board.article.command.domain.ArticleId;

public record DeleteArticleCommand(ArticleId articleId) {
    public static DeleteArticleCommand from(Long articleId) {
        return new DeleteArticleCommand(ArticleId.from(articleId));
    }
}

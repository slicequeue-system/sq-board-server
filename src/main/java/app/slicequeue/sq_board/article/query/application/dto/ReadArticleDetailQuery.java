package app.slicequeue.sq_board.article.query.application.dto;

import app.slicequeue.sq_board.article.command.domain.ArticleId;

public record ReadArticleDetailQuery(ArticleId articleId) {
    public static ReadArticleDetailQuery from(long articleId) {
        return new ReadArticleDetailQuery(ArticleId.from(articleId));
    }
}

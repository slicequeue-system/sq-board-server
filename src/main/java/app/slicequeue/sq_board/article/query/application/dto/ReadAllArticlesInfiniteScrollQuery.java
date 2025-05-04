package app.slicequeue.sq_board.article.query.application.dto;

import app.slicequeue.sq_board.article.command.domain.ArticleId;
import app.slicequeue.sq_board.board.command.domain.BoardId;

public record ReadAllArticlesInfiniteScrollQuery(BoardId boardId, long pageSize, ArticleId lastArticleId) {

    public static ReadAllArticlesInfiniteScrollQuery of(long boardId, long pageSize, Long articleId) {
        return new ReadAllArticlesInfiniteScrollQuery(
            BoardId.from(boardId),
            pageSize,
            articleId != null ? ArticleId.from(articleId) : null);
    }
}

package app.slicequeue.sq_board.article.query.application.dto;

import app.slicequeue.sq_board.article.command.domain.ArticleId;
import app.slicequeue.sq_board.board.command.domain.BoardId;
import app.slicequeue.sq_board.common.util.PageLimitCalculator;

public record ReadAllArticleInfiniteScrollQuery(BoardId boardId, long pageSize, ArticleId lastArticleId) {

    public static ReadAllArticleInfiniteScrollQuery of(long boardId, long pageSize, Long articleId) {
        return new ReadAllArticleInfiniteScrollQuery(BoardId.from(boardId), pageSize,
                articleId != null ? ArticleId.from(articleId) : null);
    }
}

package app.slicequeue.sq_board.article.query.application.dto;

import app.slicequeue.sq_board.board.command.domain.BoardId;
import app.slicequeue.sq_board.common.util.PageLimitCalculator;

public record ReadAllArticlesPagingQuery(BoardId boardId, long page, long size) {

    public static ReadAllArticlesPagingQuery of(long boardId, long page, long size) {
        return new ReadAllArticlesPagingQuery(BoardId.from(boardId), page, size);
    }

    public long offset() {
        return page * size;
    }

    public long limitForCount(int movablePageCount) {
        return PageLimitCalculator.calculatePageLimit(page, size, movablePageCount);
    }
}

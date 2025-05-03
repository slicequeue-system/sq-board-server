package app.slicequeue.sq_board.comment.query.application.dto;

import app.slicequeue.sq_board.article.command.domain.ArticleId;
import app.slicequeue.sq_board.common.util.PageLimitCalculator;

public record ReadAllCommentsPageQuery(ArticleId articleId, long page, int size, int movablePageCount) {

  public static ReadAllCommentsPageQuery of(
      long articleId, long page, int size, int movablePageCount) {
    return new ReadAllCommentsPageQuery(ArticleId.from(articleId), page, size, movablePageCount);
  }

  public long offset() {
    return (page - 1) * size;
  }

  public long limit() {
    return size;
  }

  public long limitForCount() {
    return PageLimitCalculator.calculatePageLimit(page, size, movablePageCount);
  }
}

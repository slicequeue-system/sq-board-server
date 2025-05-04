package app.slicequeue.sq_board.comment.query.application.dto;

import app.slicequeue.sq_board.article.command.domain.ArticleId;
import app.slicequeue.sq_board.comment.command.domain.CommentPath;

public record ReadAllCommentsInfiniteScrollQuery(
    ArticleId articleId,
    long pageSize,
    CommentPath lastCommentPath) {

  public static ReadAllCommentsInfiniteScrollQuery of(
      long articleId,
      long pageSize,
      String lastPath) {
    return new ReadAllCommentsInfiniteScrollQuery(
        ArticleId.from(articleId),
        pageSize,
        lastPath != null ? CommentPath.create(lastPath) : null);
  }

  public boolean isFirstPosition() {
    return lastCommentPath == null;
  }
}

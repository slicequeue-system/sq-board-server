package app.slicequeue.sq_board.comment.command.domain.dto;

import app.slicequeue.sq_board.article.command.domain.ArticleId;
import app.slicequeue.sq_board.comment.command.domain.Comment;

import java.time.Instant;

public record ArticleCommentCountCommand(ArticleId articleId, Instant lastCreatedAt) {

    public static ArticleCommentCountCommand from(Comment comment) {
        return new ArticleCommentCountCommand(comment.getArticleId(), comment.getCreatedAt());
    }

    public static ArticleCommentCountCommand from(CreateCommentCommand command) {
        return new ArticleCommentCountCommand(command.articleId(), null);
    }
}

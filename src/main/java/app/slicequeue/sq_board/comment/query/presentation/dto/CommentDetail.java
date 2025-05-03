package app.slicequeue.sq_board.comment.query.presentation.dto;

import app.slicequeue.sq_board.article.command.domain.ArticleId;
import app.slicequeue.sq_board.comment.command.domain.Comment;
import app.slicequeue.sq_board.comment.command.domain.CommentId;
import app.slicequeue.sq_board.comment.command.domain.CommentPath;
import java.time.Instant;
import java.time.OffsetDateTime;
import lombok.AccessLevel;
import lombok.Getter;

import java.util.Objects;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommentDetail {

    private String commentId;
    private String content;
    private String articleId;
    private String parentCommentId;
    private String writerId;
    private String writerNickname;
    private String commentPath;
    private Instant createdAt;
    private Instant updatedAt;

    public static CommentDetail from(Comment comment) {
        CommentDetail detail = new CommentDetail();
        detail.commentId = comment.getCommentId().toString();
        detail.content = comment.getContent();
        detail.articleId = comment.getArticleId().toString();
        detail.parentCommentId = Objects.nonNull(comment.getParentCommentId())
                ? comment.getParentCommentId().toString() : null;
        detail.writerId = comment.getWriterId().toString();
        detail.writerNickname = comment.getWriterNickname();
        detail.commentPath = comment.getPath().toString();
        detail.createdAt = comment.getCreatedAt();
        detail.updatedAt = comment.getUpdatedAt();
        return detail;
    }

    public CommentDetail(
        Long commentId,
        String content,
        Long articleId,
        Long parentCommentId,
        Long writerId,
        String writerNickname,
        String commentPath,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt) {
        this.commentId = CommentId.from(commentId).toString();
        this.content = content;
        this.articleId = ArticleId.from(articleId).toString();
        this.parentCommentId = CommentId.from(parentCommentId).toString();
        this.writerId = String.valueOf(writerId);
        this.writerNickname = writerNickname;
        this.commentPath = commentPath;
        this.createdAt = createdAt.toInstant();
        this.updatedAt = updatedAt.toInstant();
    }
}

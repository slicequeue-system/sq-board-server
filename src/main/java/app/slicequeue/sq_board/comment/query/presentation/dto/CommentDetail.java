package app.slicequeue.sq_board.comment.query.presentation.dto;

import app.slicequeue.sq_board.comment.command.domain.Comment;
import lombok.Getter;

import java.util.Objects;

@Getter
public class CommentDetail {

    private String commentId;
    private String content;
    private String articleId;
    private String parentCommentId;
    private String writerId;
    private String writerNickname;
    private String commentPath;

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
        return detail;
    }
}

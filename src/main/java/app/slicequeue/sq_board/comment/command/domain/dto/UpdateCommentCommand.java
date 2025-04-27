package app.slicequeue.sq_board.comment.command.domain.dto;

import app.slicequeue.sq_board.article.command.domain.ArticleId;
import app.slicequeue.sq_board.comment.command.domain.CommentId;
import app.slicequeue.sq_board.comment.command.domain.CommentPath;
import app.slicequeue.sq_board.comment.command.presentation.dto.CreateCommentRequest;
import app.slicequeue.sq_board.comment.command.presentation.dto.UpdateCommentRequest;

public record UpdateCommentCommand(
        CommentId commentId,
        String content,
        String writerNickname) {
    public static UpdateCommentCommand of(CommentId commentId, UpdateCommentRequest request) {
        return new UpdateCommentCommand(
                commentId,
                request.getContent(),
                request.getWriterNickname());
    }
}

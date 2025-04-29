package app.slicequeue.sq_board.comment.command.domain.dto;

import app.slicequeue.sq_board.comment.command.domain.CommentId;
import app.slicequeue.sq_board.comment.command.presentation.dto.UpdateCommentRequest;

public record DeleteCommentCommand(CommentId commentId) {
    public static DeleteCommentCommand from(CommentId commentId) {
        return new DeleteCommentCommand(commentId);
    }
}

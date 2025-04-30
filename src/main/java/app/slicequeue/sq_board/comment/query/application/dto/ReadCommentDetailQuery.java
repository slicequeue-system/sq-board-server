package app.slicequeue.sq_board.comment.query.application.dto;

import app.slicequeue.sq_board.comment.command.domain.CommentId;

public record ReadCommentDetailQuery(CommentId commentId) {
    public static ReadCommentDetailQuery from(Long id) {
        return new ReadCommentDetailQuery(CommentId.from(id));
    }
}

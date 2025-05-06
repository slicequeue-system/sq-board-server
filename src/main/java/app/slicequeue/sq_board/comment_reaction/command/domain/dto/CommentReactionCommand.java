package app.slicequeue.sq_board.comment_reaction.command.domain.dto;

import app.slicequeue.sq_board.comment.command.domain.CommentId;
import app.slicequeue.sq_board.comment_reaction.command.presentation.dto.ToggleCommentReactionRequest;

public record CommentReactionCommand(CommentId commentId, Long userId, String emoji) {

    public static CommentReactionCommand from(ToggleCommentReactionRequest request) {
        return new CommentReactionCommand(
                CommentId.from(request.getCommentId()),
                request.getUserId(),
                request.getEmoji()
        );
    }
}

package app.slicequeue.sq_board.comment_reaction.command.domain.dto;

import app.slicequeue.sq_board.comment.command.domain.CommentId;

public record CommentReactionCountCommand(CommentId commentId, String emoji) {

    public static CommentReactionCountCommand from(CommentReactionCommand command) {
        return new CommentReactionCountCommand(command.commentId(), command.emoji());
    }
}

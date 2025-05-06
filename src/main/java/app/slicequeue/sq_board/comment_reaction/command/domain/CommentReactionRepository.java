package app.slicequeue.sq_board.comment_reaction.command.domain;

import app.slicequeue.sq_board.comment.command.domain.CommentId;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentReactionRepository {

    CommentReaction save(CommentReaction commentReaction);

    void delete(CommentReaction commentReaction);

    Optional<CommentReaction> findByCommentIdAndEmojiAndUserId(CommentId commentId, String emoji, Long userId);
}

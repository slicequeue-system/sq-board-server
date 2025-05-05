package app.slicequeue.sq_board.comment_reaction.command.infra;

import app.slicequeue.sq_board.article_reaction.command.domain.ArticleReaction;
import app.slicequeue.sq_board.comment.command.domain.CommentId;
import app.slicequeue.sq_board.comment_reaction.command.domain.CommentReaction;
import app.slicequeue.sq_board.comment_reaction.command.domain.CommentReactionId;
import app.slicequeue.sq_board.comment_reaction.command.domain.CommentReactionRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JpaCommentReactionRepository extends
        CommentReactionRepository, JpaRepository<CommentReaction, CommentReactionId> {

    CommentReaction save(CommentReaction commentReaction);

    void delete(CommentReaction commentReaction);

    Optional<CommentReaction> findByCommentIdAndEmojiAndUserId(CommentId commentId, String emoji, Long userId);
}

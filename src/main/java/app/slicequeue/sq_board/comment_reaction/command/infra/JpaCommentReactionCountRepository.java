package app.slicequeue.sq_board.comment_reaction.command.infra;

import app.slicequeue.sq_board.comment_reaction.command.domain.CommentReactionCount;
import app.slicequeue.sq_board.comment_reaction.command.domain.CommentReactionCountId;
import app.slicequeue.sq_board.comment_reaction.command.domain.CommentReactionCountRepository;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JpaCommentReactionCountRepository extends
        CommentReactionCountRepository, JpaRepository<CommentReactionCount, CommentReactionCountId> {
    
    CommentReactionCount save(CommentReactionCount commentReactionCount);

    void delete(CommentReactionCount reactionCount);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<CommentReactionCount> findLockedByCommentReactionCountId(CommentReactionCountId commentReactionCountId);
}

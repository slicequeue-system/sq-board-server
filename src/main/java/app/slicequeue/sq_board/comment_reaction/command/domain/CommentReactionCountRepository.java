package app.slicequeue.sq_board.comment_reaction.command.domain;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentReactionCountRepository {
    
    CommentReactionCount save(CommentReactionCount commentReactionCount);

    void delete(CommentReactionCount reactionCount);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<CommentReactionCount> findLockedByCommentReactionCountId(CommentReactionCountId commentReactionCountId);
}

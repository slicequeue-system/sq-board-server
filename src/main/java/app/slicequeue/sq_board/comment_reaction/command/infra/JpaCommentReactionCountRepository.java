package app.slicequeue.sq_board.comment_reaction.command.infra;

import app.slicequeue.sq_board.comment_reaction.command.domain.CommentReactionCount;
import app.slicequeue.sq_board.comment_reaction.command.domain.CommentReactionCountId;
import app.slicequeue.sq_board.comment_reaction.command.domain.CommentReactionCountRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaCommentReactionCountRepository extends
        CommentReactionCountRepository, JpaRepository<CommentReactionCount, CommentReactionCountId> {
    
    CommentReactionCount save(CommentReactionCount commentReactionCount);
}

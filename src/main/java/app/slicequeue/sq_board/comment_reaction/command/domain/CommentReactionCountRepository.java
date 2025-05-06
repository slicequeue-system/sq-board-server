package app.slicequeue.sq_board.comment_reaction.command.domain;

import app.slicequeue.sq_board.article_reaction.command.domain.ArticleReactionCount;
import app.slicequeue.sq_board.article_reaction.command.domain.ArticleReactionCountId;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentReactionCountRepository {
    
    CommentReactionCount save(CommentReactionCount commentReactionCount);

    Optional<CommentReactionCount> findLockedByCommentReactionCountId(CommentReactionCountId commentReactionCountId);
}

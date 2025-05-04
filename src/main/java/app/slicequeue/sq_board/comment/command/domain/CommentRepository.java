package app.slicequeue.sq_board.comment.command.domain;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentRepository {

    Comment save(Comment comment);

    Optional<Comment> findByCommentId(CommentId commentId);

    Optional<Comment> findByPath(CommentPath path);

    Optional<String> findDescendantsTopPath(@Param("articleId") Long articleId,
                                               @Param("pathPrefix") String pathPrefix);
}

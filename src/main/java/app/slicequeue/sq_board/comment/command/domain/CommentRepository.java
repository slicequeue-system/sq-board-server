package app.slicequeue.sq_board.comment.command.domain;

import app.slicequeue.sq_board.article.command.domain.ArticleId;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentRepository {

    Comment save(Comment comment);

    Optional<Comment> findByCommentId(CommentId commentId);

    Optional<Comment> findByPath(CommentPath path);

    Optional<CommentPath> findDescendantsTopPath(@Param("articleId") ArticleId articleId,
                                               @Param("pathPrefix") String pathPrefix);
}

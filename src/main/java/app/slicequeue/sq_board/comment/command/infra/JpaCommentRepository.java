package app.slicequeue.sq_board.comment.command.infra;

import app.slicequeue.sq_board.article.command.domain.ArticleId;
import app.slicequeue.sq_board.comment.command.domain.Comment;
import app.slicequeue.sq_board.comment.command.domain.CommentId;
import app.slicequeue.sq_board.comment.command.domain.CommentPath;
import app.slicequeue.sq_board.comment.command.domain.CommentRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface JpaCommentRepository extends JpaRepository<Comment, CommentId>, CommentRepository {

    Comment save(Comment comment);

    Optional<Comment> findByCommentId(CommentId commentId);

    Optional<Comment> findByPath(CommentPath path);

    @Query("""
            select c.path from Comment c
            where c.articleId = :articleId
            and c.path.path > :pathPrefix and c.path.path like :pathPrefix%
            order by path.path desc limit 1
            """)
    Optional<CommentPath> findDescendantsTopPath(
            @Param("articleId") ArticleId articleId,
            @Param("pathPrefix") String pathPrefix);
}

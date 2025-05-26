package app.slicequeue.sq_board.comment.command.infra;

import app.slicequeue.sq_board.comment.command.domain.Comment;
import app.slicequeue.sq_board.comment.command.domain.CommentId;
import app.slicequeue.sq_board.comment.command.domain.CommentPath;
import app.slicequeue.sq_board.comment.command.domain.CommentRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface JpaCommentRepository extends JpaRepository<Comment, CommentId>, CommentRepository {

    Comment save(Comment comment);

    Optional<Comment> findByCommentId(CommentId commentId);

    Optional<Comment> findByPath(CommentPath path);

    @Query(value = """
            SELECT c.path
            FROM comment c
            WHERE c.article_id = :articleId
              AND c.path > :pathPrefix
              AND c.path LIKE CONCAT(:pathPrefix, '%')
            ORDER BY c.path DESC
            LIMIT 1
            """, nativeQuery = true)
    Optional<String> findDescendantsTopPath(
            @Param("articleId") Long articleId,
            @Param("pathPrefix") String pathPrefix);

    @Modifying
    @Transactional
    @Query(value = """
            UPDATE comment SET writer_nickname = :nickname WHERE writer_id = :userId;
            """, nativeQuery = true)
    int updateUserNicknameByUserId(@Param("nickname") String nickname, @Param("userId") long userId);
}

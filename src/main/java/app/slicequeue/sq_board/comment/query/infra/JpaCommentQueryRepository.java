package app.slicequeue.sq_board.comment.query.infra;

import app.slicequeue.sq_board.article.command.domain.ArticleId;
import app.slicequeue.sq_board.comment.command.domain.Comment;
import app.slicequeue.sq_board.comment.command.domain.CommentId;
import app.slicequeue.sq_board.comment.command.domain.CommentPath;
import app.slicequeue.sq_board.comment.query.presentation.dto.CommentDetail;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public interface JpaCommentQueryRepository extends JpaRepository<Comment, CommentId>,
        JpaCommentQueryRepositoryCustom {

    @Query(value = """
            SELECT
                  t2.comment_id,
                  t2.content,
                  t2.article_id,
                  t2.parent_comment_id,
                  t2.writer_id,
                  t2.writer_nickname,
                  t2.path,
                  t2.deleted,
                  t2.created_at,
                  t2.updated_at
            FROM (
              SELECT comment_id
              FROM comment
              WHERE article_id = :articleId
              ORDER BY path ASC
              limit :limit OFFSET :offset
            ) t1 LEFT JOIN comment t2 ON t1.comment_id = t2.comment_id;
            """, nativeQuery = true)
    List<Comment> findAllBy(
            @Param("articleId") long articleId,
            @Param("limit") long limit,
            @Param("offset") long offset);

    @Query(value = """
            SELECT
                  t2.comment_id,
                  t2.content,
                  t2.article_id,
                  t2.parent_comment_id,
                  t2.writer_id,
                  t2.writer_nickname,
                  t2.path,
                  t2.created_at,
                  t2.updated_at
            FROM (
              SELECT comment_id
              FROM comment
              WHERE article_id = :articleId
              ORDER BY path ASC
              LIMIT :limit OFFSET :offset
            ) t1 LEFT JOIN comment t2 ON t1.comment_id = t2.comment_id;
            """, nativeQuery = true)
    List<CommentDetail> findAllCommentDetailsBy( // FIXME H2 테스트에서는 CommentDetail 생성자 맵핑 되나 mysql 에서는 안됨
                                                 @Param("articleId") long articleId,
                                                 @Param("limit") long limit,
                                                 @Param("offset") long offset);

    @Query(value = """
            SELECT
                new app.slicequeue.sq_board.comment.query.presentation.dto.CommentDetail(
                  c.commentId,
                  c.content,
                  c.articleId,
                  c.parentCommentId,
                  c.writerId,
                  c.writerNickname,
                  c.path,
                  c.createdAt,
                  c.updatedAt
                )
            FROM Comment c
            WHERE c.articleId = :articleId
            ORDER BY c.path ASC
            LIMIT :pageSize
            """)
    List<CommentDetail> findAllCommentDetailsInfiniteScroll(
            @Param("articleId") ArticleId articleId, @Param("pageSize") long pageSize);

    @Query(value = """
            SELECT
                new app.slicequeue.sq_board.comment.query.presentation.dto.CommentDetail(
                  c.commentId,
                  c.content,
                  c.articleId,
                  c.parentCommentId,
                  c.writerId,
                  c.writerNickname,
                  c.path,
                  c.createdAt,
                  c.updatedAt
                )
            FROM Comment c
            WHERE c.articleId = :articleId AND c.path > :lastPath
            ORDER BY c.path ASC
            LIMIT :pageSize
            """)
    List<CommentDetail> findAllCommentDetailsInfiniteScroll(
            @Param("articleId") ArticleId articleId,
            @Param("pageSize") long pageSize,
            @Param("lastPath") CommentPath lastPath);


    @Query(value = """
            SELECT count(*) FROM (
                SELECT comment_id FROM comment
                WHERE article_id = :articleId
                LIMIT :limit
            ) t1;
            """, nativeQuery = true)
    long count(@Param("articleId") long articleId, @Param("limit") long limit);


}

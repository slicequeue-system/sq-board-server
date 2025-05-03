package app.slicequeue.sq_board.article.query.infra;

import app.slicequeue.sq_board.article.command.domain.Article;
import app.slicequeue.sq_board.article.command.domain.ArticleId;
import app.slicequeue.sq_board.article.query.presentation.dto.ArticleDetail;
import app.slicequeue.sq_board.article.query.presentation.dto.ArticleListItem;
import app.slicequeue.sq_board.board.command.domain.BoardId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface JpaArticleQueryRepository extends JpaRepository<Article, ArticleId> {

    @Query(value = """
                SELECT
                    t2.article_id, t2.board_id, t2.title, t2.writer_id, t2.writer_name, t2.tags, t2.content, t2.deleted_at, t2.created_at, t2.updated_at
                FROM (
                    SELECT article_id
                    FROM article
                    WHERE board_id = :boardId
                        AND deleted_at IS NULL
                    ORDER BY article_id desc
                    LIMIT :limit OFFSET :offset
                ) t1 LEFT JOIN article t2 ON t1.article_id = t2.article_id;
                
            """, nativeQuery = true)
    List<Article> findAllBy(
            @Param("boardId") long boardId,
            @Param("offset") long offset,
            @Param("limit") long limit);


    @Query(value = """
                SELECT
                    t2.article_id, t2.title, t2.writer_name, t2.created_at, t2.updated_at
                FROM (
                    SELECT article_id
                    FROM article
                    WHERE board_id = :boardId
                        AND deleted_at IS NULL
                    ORDER BY article_id desc
                    LIMIT :limit OFFSET :offset
                ) t1 left join article t2 on t1.article_id = t2.article_id;
            """, nativeQuery = true)
    List<ArticleListItem> findAllArticleListItemBy( // FIXME H2 테스트에서는 ArticleListItem 생성자 맵핑 되나 mysql 에서는 안됨
                                                    @Param("boardId") Long boardId,
                                                    @Param("offset") long offset,
                                                    @Param("limit") long limit);

    @Query(value = """
                SELECT count(*) from (
                    SELECT article_id FROM article
                    WHERE board_id = :boardId
                    LIMIT :limit
                ) t1;
            """, nativeQuery = true)
    long count(@Param("boardId") long boardId, @Param("limit") long limit);


    @Query("""
            SELECT new app.slicequeue.sq_board.article.query.presentation.dto.ArticleListItem(
                a.articleId,
                a.title,
                a.writerName,
                a.createdAt,
                a.updatedAt
            )
            FROM Article a
            WHERE a.boardId = :boardId
            ORDER BY a.articleId.id DESC
            LIMIT :pageSize
            """)
    List<ArticleListItem> findAllArticleListItemInfiniteScroll(
            @Param("boardId") BoardId boardId,
            @Param("pageSize") long pageSize);

    @Query("""
            SELECT new app.slicequeue.sq_board.article.query.presentation.dto.ArticleListItem(
                a.articleId,
                a.title,
                a.writerName,
                a.createdAt,
                a.updatedAt
            )
            FROM Article a
            WHERE a.boardId = :boardId AND a.articleId < :lastArticleId
            ORDER BY a.articleId.id desc
            LIMIT :pageSize
            """)
    List<ArticleListItem> findAllArticleListItemInfiniteScroll(
            @Param("boardId") BoardId boardId,
            @Param("pageSize") long pageSize,
            @Param("lastArticleId") ArticleId lastArticleId);


    @Query("""
            SELECT new app.slicequeue.sq_board.article.query.presentation.dto.ArticleDetail(
                a.articleId,
                a.title,
                a.content,
                a.writerName,
                a.tags
            )
            FROM Article a
            WHERE a.articleId = :articleId
            """)
    Optional<ArticleDetail> findArticleDetailBy(@Param("articleId") ArticleId articleId);
}

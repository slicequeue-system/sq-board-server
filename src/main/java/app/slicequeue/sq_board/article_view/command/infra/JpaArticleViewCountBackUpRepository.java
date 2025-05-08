package app.slicequeue.sq_board.article_view.command.infra;

import app.slicequeue.sq_board.article.command.domain.ArticleId;
import app.slicequeue.sq_board.article_view.command.domain.ArticleViewCount;
import app.slicequeue.sq_board.article_view.command.domain.ArticleViewCountBackUpRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaArticleViewCountBackUpRepository extends
        ArticleViewCountBackUpRepository, JpaRepository<ArticleViewCount, ArticleId> {

    @Query("""
            update ArticleViewCount a set a.viewCount = :viewCount
               where a.articleId = :articleId and viewCount < :viewCount
            """)
    @Modifying
    int updateViewCount(
            @Param("articleId") ArticleId articleId,
            @Param("viewCount") long viewCount
    );
}

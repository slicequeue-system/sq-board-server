package app.slicequeue.sq_board.article_view.command.domain;

import app.slicequeue.sq_board.article.command.domain.ArticleId;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ArticleViewCountBackUpRepository {

    ArticleViewCount save(ArticleViewCount init);

    @Modifying
    int updateViewCount(
            @Param("articleId") ArticleId articleId,
            @Param("viewCount") long viewCount
    );

    Optional<ArticleViewCount> findById(ArticleId articleId);
}

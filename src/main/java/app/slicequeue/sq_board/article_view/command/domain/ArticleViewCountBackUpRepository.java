package app.slicequeue.sq_board.article_view.command.domain;

import app.slicequeue.sq_board.article.command.domain.ArticleId;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;

public interface ArticleViewCountBackUpRepository {

    @Modifying
    int updateViewCount(
            @Param("articleId") ArticleId articleId,
            @Param("viewCount") long viewCount
    );
}

package app.slicequeue.sq_board.article_view.command.domain;

import app.slicequeue.sq_board.article.command.domain.ArticleId;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleViewCountRepository {

    long read(ArticleId articleId);
    long increase(ArticleId articleId);
}

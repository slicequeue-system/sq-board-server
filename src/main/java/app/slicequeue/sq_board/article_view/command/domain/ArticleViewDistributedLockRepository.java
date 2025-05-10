package app.slicequeue.sq_board.article_view.command.domain;


import app.slicequeue.sq_board.article.command.domain.ArticleId;
import org.springframework.stereotype.Repository;

import java.time.Duration;

@Repository
public interface ArticleViewDistributedLockRepository {

    boolean lock(ArticleId articleId, long userId, Duration ttl);
}

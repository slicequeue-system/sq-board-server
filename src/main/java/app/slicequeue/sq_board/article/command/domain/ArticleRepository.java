package app.slicequeue.sq_board.article.command.domain;

import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ArticleRepository {

    Article save(Article board);

    Optional<Article> findByArticleIdAndDeletedAtIsNull(ArticleId articleId);
}

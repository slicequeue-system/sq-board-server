package app.slicequeue.sq_board.article_view.command.domain;

import app.slicequeue.sq_board.article.command.domain.ArticleId;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleViewCountRepository {

    long read(@NotNull ArticleId articleId);
    long increase(@NotNull ArticleId articleId);
    void insertIfAbsent(@NotNull ArticleViewCount count);
}

package app.slicequeue.sq_board.article.query.presentation.dto;

import app.slicequeue.sq_board.article.command.domain.Article;
import lombok.Getter;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.List;

@Getter
public class ArticlePageResponse {

    private List<ArticleListItem> articles;
    private long count;

    public static ArticlePageResponse of(List<ArticleListItem> articles, long count) {
        ArticlePageResponse response = new ArticlePageResponse();
        response.articles = articles;
        response.count = count;
        return response;
    }
}

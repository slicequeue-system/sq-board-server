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

    @Getter
    public static class ArticleListItem {

        private String articleId;
        private String title;
        private String writerName;
        private Instant createdAt;
        private Instant updatedAt;

        private ArticleListItem() {}

        public ArticleListItem(
                Long articleId,
                String title,
                String writerName,
                OffsetDateTime createdAt,
                OffsetDateTime updatedAt) {
            this.articleId = String.valueOf(articleId);
            this.title = title;
            this.writerName = writerName;
            this.createdAt = createdAt.toInstant();
            this.updatedAt = updatedAt.toInstant();
        }

        public static ArticleListItem from(Article article) {
            ArticleListItem articleListItem = new ArticleListItem();
            articleListItem.articleId = article.getArticleId().toString();
            articleListItem.title = article.getTitle();
            articleListItem.writerName = article.getWriterName();
            articleListItem.createdAt = article.getCreatedAt();
            articleListItem.updatedAt = article.getUpdatedAt();
            return articleListItem;
        }
    }
}

package app.slicequeue.sq_board.article_view.command.domain;

import app.slicequeue.sq_board.article.command.domain.ArticleId;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Table(name = "article_view_count")
@Getter
@Entity
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ArticleViewCount {

    @EmbeddedId
    @AttributeOverride(name = "id", column = @Column(name = "article_id"))
    private ArticleId articleId; // shared key
    private long viewCount;

    public static ArticleViewCount init(ArticleId articleId, long viewCount) {
        ArticleViewCount articleViewCount = new ArticleViewCount();
        articleViewCount.articleId = articleId;
        articleViewCount.viewCount = viewCount;
        return articleViewCount;
    }

}

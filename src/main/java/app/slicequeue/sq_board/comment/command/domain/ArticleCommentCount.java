package app.slicequeue.sq_board.comment.command.domain;

import app.slicequeue.sq_board.article.command.domain.ArticleId;
import app.slicequeue.sq_board.comment.command.domain.dto.ArticleCommentCountCommand;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Getter
@Table(name = "article_comment_count")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ArticleCommentCount {

    @EmbeddedId
    @AttributeOverride(name = "id", column = @Column(name = "article_id"))
    private ArticleId articleId;
    private long commentCount;
    private Instant lastCreatedAt;

    public static ArticleCommentCount createCountZero(ArticleCommentCountCommand command) {
        ArticleCommentCount articleCommentCount = new ArticleCommentCount();
        articleCommentCount.articleId = command.articleId();
        articleCommentCount.commentCount = 0;
        articleCommentCount.lastCreatedAt = Instant.now();
        return articleCommentCount;
    }

    public void increaseCount(Instant commentLastCreatedAt) {
        commentCount = commentCount + 1;
        lastCreatedAt = commentLastCreatedAt;
    }

    public void decreaseCount() {
        if (commentCount == 0) return;
        commentCount = commentCount - 1;
    }
}

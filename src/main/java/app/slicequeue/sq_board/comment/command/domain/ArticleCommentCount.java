package app.slicequeue.sq_board.comment.command.domain;

import app.slicequeue.sq_board.article.command.domain.ArticleId;
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

}

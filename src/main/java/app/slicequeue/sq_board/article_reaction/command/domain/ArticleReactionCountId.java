package app.slicequeue.sq_board.article_reaction.command.domain;

import app.slicequeue.sq_board.article.command.domain.ArticleId;
import app.slicequeue.sq_board.article_reaction.command.domain.dto.ArticleReactionCountCommand;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ArticleReactionCountId implements Serializable {

    @NotNull
    @AttributeOverride(name = "id", column = @Column(name = "article_id"))
    private ArticleId articleId; // shard key
    @NotNull
    private String emoji;

    public ArticleReactionCountId(ArticleId articleId, String emoji) {
        this.articleId = articleId;
        this.emoji = emoji;
    }

    public static ArticleReactionCountId from(ArticleReactionCountCommand command) {
        return new ArticleReactionCountId(command.articleId(), command.emoji());
    }
}

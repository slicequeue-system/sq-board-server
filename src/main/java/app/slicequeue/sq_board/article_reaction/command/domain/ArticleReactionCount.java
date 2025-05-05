package app.slicequeue.sq_board.article_reaction.command.domain;

import app.slicequeue.sq_board.article_reaction.command.domain.dto.IncreaseArticleReactionCountCommand;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

@Getter
@Table(name = "article_reaction_count", indexes = {
        @Index(name = "unq_article_id_emoji", columnList = "article_id,emoji", unique = true)
})
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ArticleReactionCount {

    @EmbeddedId
    private ArticleReactionCountId articleReactionCountId;
    @NotNull
    private int count;

    public static ArticleReactionCount createCountZero(IncreaseArticleReactionCountCommand command) {
        Assert.notNull(command, "command must not be null.");
        ArticleReactionCount articleReaction = new ArticleReactionCount();
        articleReaction.articleReactionCountId = ArticleReactionCountId.from(command);
        articleReaction.count = 0;
        return articleReaction;
    }

    public void increaseCount() {
        count = count + 1;
    }
}

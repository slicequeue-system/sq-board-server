package app.slicequeue.sq_board.article_reaction.command.domain;

import app.slicequeue.sq_board.article.command.domain.ArticleId;
import app.slicequeue.sq_board.article_reaction.command.domain.dto.ArticleReactionCommand;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import java.time.Instant;

@Getter
@Table(name = "article_reaction", indexes = {
        @Index(name = "unq_article_id_emoji_user_id", columnList = "article_id,emoji,user_id", unique = true)
})
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ArticleReaction {

    @EmbeddedId
    @AttributeOverride(name = "id", column = @Column(name = "article_reaction_id"))
    private ArticleReactionId articleReactionId;
    @NotNull
    @AttributeOverride(name = "id", column = @Column(name = "article_id"))
    private ArticleId articleId; // shard key
    @NotNull
    private String emoji;
    private Long userId;
    private Instant createdAt;

    public static ArticleReaction create(ArticleReactionCommand command) {
        Assert.notNull(command, "command must not be null.");
        ArticleReaction articleReaction = new ArticleReaction();
        articleReaction.articleReactionId = ArticleReactionId.generateId();
        articleReaction.articleId = command.articleId();
        articleReaction.emoji = validateEmoji(command.emoji());
        articleReaction.userId = command.userId();
        articleReaction.createdAt = Instant.now();
        return articleReaction;
    }

    private static String validateEmoji(String emoji) {
        Assert.notNull(emoji, "emoji must not be null.");
        return emoji.trim();
    }
}

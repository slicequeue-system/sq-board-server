package app.slicequeue.sq_board.comment_reaction.command.domain;

import app.slicequeue.sq_board.comment.command.domain.CommentId;
import app.slicequeue.sq_board.comment_reaction.command.domain.dto.CommentReactionCommand;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import java.time.Instant;

@Getter
@Table(name = "comment_reaction", indexes = {
        @Index(name = "unq_comment_id_emoji_user_id", columnList = "comment_id,emoji,user_id", unique = true)
})
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentReaction {

    @EmbeddedId
    @AttributeOverride(name = "id", column = @Column(name = "comment_reaction_id"))
    private CommentReactionId commentReactionId;
    @NotNull
    @AttributeOverride(name = "id", column = @Column(name = "comment_id"))
    private CommentId commentId; // shard key
    @NotNull
    private String emoji;
    private Long userId;
    private Instant createdAt;

    public static CommentReaction create(CommentReactionCommand command) {
        Assert.notNull(command, "command must not be null.");
        CommentReaction reaction = new CommentReaction();
        reaction.commentReactionId = CommentReactionId.generateId();
        reaction.commentId = command.commentId();
        reaction.emoji = validateEmoji(command.emoji());
        reaction.userId = command.userId();
        reaction.createdAt = Instant.now();
        return reaction;
    }

    private static String validateEmoji(String emoji) {
        Assert.notNull(emoji, "emoji must not be null.");
        return emoji.trim();
    }
}

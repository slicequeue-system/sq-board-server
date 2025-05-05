package app.slicequeue.sq_board.comment_reaction.command.domain;

import app.slicequeue.sq_board.comment_reaction.command.domain.dto.CommentReactionCountCommand;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

@Getter
@Table(name = "comment_reaction_count")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentReactionCount {

    @EmbeddedId
    private CommentReactionCountId commentReactionCountId;
    @NotNull
    private int count;

    public static CommentReactionCount createCountZero(CommentReactionCountCommand command) {
        Assert.notNull(command, "command must not be null.");
        CommentReactionCount articleReaction = new CommentReactionCount();
        articleReaction.commentReactionCountId = CommentReactionCountId.from(command);
        articleReaction.count = 0;
        return articleReaction;
    }

    public void increaseCount() {
        count = count + 1;
    }

    public void decreaseCount() {
        if (count == 0) return;
        count = count - 1;
    }
}

package app.slicequeue.sq_board.comment_reaction.command.domain;

import app.slicequeue.sq_board.article.command.domain.ArticleId;
import app.slicequeue.sq_board.article_reaction.command.domain.dto.ArticleReactionCountCommand;
import app.slicequeue.sq_board.comment.command.domain.CommentId;
import app.slicequeue.sq_board.comment_reaction.command.domain.dto.CommentReactionCountCommand;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentReactionCountId implements Serializable {

    @NotNull
    @AttributeOverride(name = "id", column = @Column(name = "comment_id"))
    private CommentId commentId; // shard key
    @NotNull
    private String emoji;

    public CommentReactionCountId(CommentId commentId, String emoji) {
        this.commentId = commentId;
        this.emoji = emoji;
    }

    public static CommentReactionCountId from(CommentReactionCountCommand command) {
        return new CommentReactionCountId(command.commentId(), command.emoji());
    }
}

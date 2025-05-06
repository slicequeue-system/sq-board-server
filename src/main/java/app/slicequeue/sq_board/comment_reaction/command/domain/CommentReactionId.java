package app.slicequeue.sq_board.comment_reaction.command.domain;

import app.slicequeue.common.base.id_entity.BaseSnowflakeId;
import app.slicequeue.common.snowflake.Snowflake;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class CommentReactionId extends BaseSnowflakeId<CommentReactionId> {

    private static Snowflake snowflake = new Snowflake();

    public CommentReactionId(Long id) {
        super(id);
    }

    @Override
    protected Snowflake getSnowflake() {
        return snowflake;
    }

    public static CommentReactionId generateId() {
        return new CommentReactionId().generate();
    }

    public static CommentReactionId from(Long idValue) {
        return from(idValue, CommentReactionId.class);
    }
}

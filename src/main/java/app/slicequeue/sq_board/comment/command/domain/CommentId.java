package app.slicequeue.sq_board.comment.command.domain;

import app.slicequeue.common.base.id_entity.BaseSnowflakeId;
import app.slicequeue.common.snowflake.Snowflake;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class CommentId extends BaseSnowflakeId<CommentId> {

    static Snowflake snowflake = new Snowflake();

    public CommentId(Long id) {
        super(id);
    }

    public static CommentId generateId() {
        return new CommentId().generate();
    }

    public static CommentId from(Long idValue) {
        return from(idValue, CommentId.class);
    }

    @Override
    protected Snowflake getSnowflake() {
        return snowflake;
    }
}

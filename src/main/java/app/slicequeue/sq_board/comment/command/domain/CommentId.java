package app.slicequeue.sq_board.comment.command.domain;

import app.slicequeue.common.snowflake.Snowflake;
import app.slicequeue.sq_board.common.base.AbstractSnowflakeId;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class CommentId extends AbstractSnowflakeId<CommentId> {

    static Snowflake snowflake = new Snowflake();

    public CommentId(long id) {
        super(id);
    }

    public static CommentId generateId() {
        return new CommentId(snowflake.nextId());
    }

    @Override
    protected Snowflake getSnowflake() {
        return snowflake;
    }
}

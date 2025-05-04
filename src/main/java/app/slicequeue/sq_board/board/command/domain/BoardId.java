package app.slicequeue.sq_board.board.command.domain;

import app.slicequeue.common.base.id_entity.BaseSnowflakeId;
import app.slicequeue.common.snowflake.Snowflake;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class BoardId extends BaseSnowflakeId<BoardId> {

    static Snowflake snowflake = new Snowflake();

    public BoardId(Long id) {
        super(id);
    }

    @Override
    protected Snowflake getSnowflake() {
        return snowflake;
    }

    public static BoardId generateId() {
        return new BoardId().generate();
    }

    public static BoardId from(Long idValue) {
        return from(idValue, BoardId.class);
    }
}

package app.slicequeue.sq_board.board.command.domain;

import app.slicequeue.common.base.id_entity.BaseSnowflakeId;
import app.slicequeue.common.snowflake.Snowflake;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.springframework.util.Assert;

import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class BoardId extends BaseSnowflakeId<BoardId> {

    static Snowflake snowflake = new Snowflake();

    private BoardId(long id) {super(id);
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

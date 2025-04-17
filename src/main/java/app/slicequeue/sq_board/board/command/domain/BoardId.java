package app.slicequeue.sq_board.board.command.domain;

import app.slicequeue.common.snowflake.Snowflake;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.springframework.util.Assert;

import java.util.Objects;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class BoardId {

    static Snowflake snowflake = new Snowflake();

    @NotNull
    @Comment("게시판식별값")
    @Column(name = "board_id")
    private Long id;

    private BoardId(long id) {
        this.id = id;
    }

    public static BoardId generateId() {
        BoardId boardId = new BoardId();
        boardId.id = snowflake.nextId();
        return boardId;
    }

    public static BoardId from(Long idValue) {
        Assert.notNull(idValue, "id must not be null");
        if (idValue <= 0)
            throw new IllegalArgumentException("id must be positive value");
        return new BoardId(idValue);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BoardId boardId)) return false;
        return Objects.equals(getId(), boardId.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return "BoardId{" +
                "id=" + id +
                '}';
    }
}

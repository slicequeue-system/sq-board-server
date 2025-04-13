package app.slicequeue.sq_board.board.command.domain;

import app.slicequeue.common.snowflake.Snowflake;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import java.util.Objects;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Embeddable
public class BoardId {

    @NotNull
    @Comment("게시판식별값")
    @Column(name = "board_id")
    private Long id;


    public static BoardId generateId() {
        BoardId boardId = new BoardId();
        Snowflake snowflake = new Snowflake();
        boardId.id = snowflake.nextId();
        return boardId;
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

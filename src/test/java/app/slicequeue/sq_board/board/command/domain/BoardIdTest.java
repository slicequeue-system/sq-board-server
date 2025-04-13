package app.slicequeue.sq_board.board.command.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


class BoardIdTest {

    @Test
    void generateId() {
        BoardId boardId = BoardId.generateId();
        assertThat(boardId).isNotNull();
        assertThat(boardId.getId()).isNotNull().isNotZero().isPositive();
    }

    @Test
    void from() {
        // given
        Long value = 123L;
        Long wrongValue1 = null;
        Long wrongValue2 = -1L;
        Long wrongValue3 = 0L;

        assertThat(BoardId.from(value)).isNotNull();
        assertThatThrownBy(() -> BoardId.from(wrongValue1)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> BoardId.from(wrongValue2)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> BoardId.from(wrongValue3)).isInstanceOf(IllegalArgumentException.class);
    }
}

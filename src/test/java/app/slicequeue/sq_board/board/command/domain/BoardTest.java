package app.slicequeue.sq_board.board.command.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class BoardTest {

    @Autowired
    TestEntityManager entityManager;

    @Test
    void 게시판엔티티_생성을_확인한다() {
        // given
        Board board = Board.create("게시판1", 1L, 1L, "게시판 설명");
        entityManager.persistAndFlush(board);
        entityManager.detach(board);

        // when
        Board found = entityManager.find(Board.class, board.getBoardId());

        // then
        assertThat(found.getBoardId()).isEqualTo(board.getBoardId());
        assertThat(found.getName()).isEqualTo(board.getName());
        assertThat(found.getProjectId()).isEqualTo(board.getProjectId());
        assertThat(found.getAdminId()).isEqualTo(board.getAdminId());
    }
}

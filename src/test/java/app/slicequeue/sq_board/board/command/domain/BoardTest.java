package app.slicequeue.sq_board.board.command.domain;

import app.slicequeue.sq_board.board.command.domain.dto.CreateBoardCommand;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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

    @ParameterizedTest
    @MethodSource("invalidCreateBoardArguments")
    void 잘못된_요청값으로_게시판_생성시_예외가_발생한다(CreateBoardCommand command, String expectedExceptionMessage) { // given
        // when & then
        assertThatThrownBy(() -> Board.create(command))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(expectedExceptionMessage);
    }

    static Stream<Arguments> invalidCreateBoardArguments() {
        /* CreateBoardCommand command, String expectedExceptionMessage */
        return Stream.of(
                Arguments.of(new CreateBoardCommand(null, 1L, 1L, "설명"), "name must not be null"),
                Arguments.of(new CreateBoardCommand(" ", 1L, 1L, "설명"), "name must not be blank"),
                Arguments.of(new CreateBoardCommand("게시판명", null, 1L, "설명"), "projectId must not be null"),
                Arguments.of(new CreateBoardCommand("게시판명", 1L, null, "설명"), "adminId must not be null")
        );
    }

}

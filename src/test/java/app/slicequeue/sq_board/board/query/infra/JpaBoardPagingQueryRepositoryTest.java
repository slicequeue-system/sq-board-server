package app.slicequeue.sq_board.board.query.infra;

import app.slicequeue.sq_board.board.command.domain.Board;
import app.slicequeue.sq_board.board.BoardTestFixture;
import app.slicequeue.sq_board.board.query.presentation.dto.BoardListItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.data.domain.Sort.Direction.DESC;

@DataJpaTest
class JpaBoardPagingQueryRepositoryTest {

    Long mockProjectId = 1L;
    Long mockAdminId = 1L;

    @Autowired
    JpaBoardPagingQueryRepository jpaBoardPagingQueryRepository;

    @BeforeEach
    void init() {
        List<Board> boards = BoardTestFixture.createBoardExamples(10, mockProjectId, mockAdminId);
        for (Board board : boards) {
            jpaBoardPagingQueryRepository.saveAndFlush(board);
        }
    }

    @ParameterizedTest
    @MethodSource("pagingArguments")
    void 페이징인자를_통해_복수조회를_한다(int pageNumber, int pageSize, int expectedCount) {
        // given
        PageRequest page = PageRequest.of(pageNumber, pageSize, Sort.by(DESC, "createdAt"));
        Long projectId = mockProjectId;

        // when
        Page<BoardListItem> results = jpaBoardPagingQueryRepository.findAllBoardListItemsByProjectId(projectId, page);

        // then
        assertThat(results).hasSize(expectedCount);
        assertThat(results.stream().map(BoardListItem::getCreatedAt)).isSortedAccordingTo(Comparator.reverseOrder());
    }

    public static Stream<Arguments> pagingArguments() {
        return Stream.of(
                Arguments.of(0, 3, 3),
                Arguments.of(1, 3, 3),
                Arguments.of(2, 3, 3),
                Arguments.of(3, 3, 1)
        );
    }

}

package app.slicequeue.sq_board.board.query.infra;

import app.slicequeue.sq_board.board.BoardTestFixture;
import app.slicequeue.sq_board.board.command.domain.Board;
import app.slicequeue.sq_board.board.command.domain.BoardId;
import app.slicequeue.sq_board.board.query.presentation.dto.BoardListItem;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
        List<Board> boardList = BoardTestFixture.createBoardExamples(10, mockProjectId, mockAdminId);
        for (Board board : boardList) {
            jpaBoardPagingQueryRepository.saveAndFlush(board);
        }
    }

    @AfterEach
    void clear() {
        jpaBoardPagingQueryRepository.deleteAll();
    }

    @ParameterizedTest
    @MethodSource("pagingArguments")
    void 페이징인자를_통해_페이징복수조회를_한다(int pageNumber, int pageSize, int expectedCount) {
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

    @Test
    void 무한스크롤인자를_통해_무한스크롤복수조회를_한다() {
        // given
        Long projectId = mockProjectId;
        long pageSize = 3;
        List<Board> all =
                jpaBoardPagingQueryRepository.findAll().stream()
                        .sorted(Comparator.comparing(Board::getCreatedAt).reversed()).toList();

        // when
        List<BoardListItem> results1 = jpaBoardPagingQueryRepository.findAllBoardListItemsInfiniteScroll(projectId,
                pageSize);
        List<BoardListItem> results2 = jpaBoardPagingQueryRepository.findAllBoardListItemsInfiniteScroll(projectId,
                pageSize, Long.valueOf(results1.getLast().getBoardId()));
        List<BoardListItem> results3 = jpaBoardPagingQueryRepository.findAllBoardListItemsInfiniteScroll(projectId,
                pageSize, Long.valueOf(results2.getLast().getBoardId()));
        List<BoardListItem> results4 = jpaBoardPagingQueryRepository.findAllBoardListItemsInfiniteScroll(projectId,
                pageSize, Long.valueOf(results3.getLast().getBoardId()));
        List<BoardListItem> results5 = jpaBoardPagingQueryRepository.findAllBoardListItemsInfiniteScroll(projectId,
                pageSize, Long.valueOf(results4.getLast().getBoardId()));

        // then
        assertThat(results1).hasSize((int) pageSize);
        assertThat(results2).hasSize((int) pageSize);
        assertThat(results3).hasSize((int) pageSize);
        assertThat(results4).hasSize(1);
        assertThat(results5).isEmpty();

        BoardId expectedFirstBoardId = all.getFirst().getBoardId();
        BoardId expectedLastBoardId = all.getLast().getBoardId();
        assertThat(results1.getFirst().getBoardId()).isEqualTo(String.valueOf(expectedFirstBoardId.getId()));
        assertThat(results4.getLast().getBoardId()).isEqualTo(String.valueOf(expectedLastBoardId.getId()));

        assertThat(Stream.concat(Stream.concat(Stream.concat(results1.stream(), results2.stream()), results3.stream()),
                results4.stream())).isSortedAccordingTo(Comparator.comparing(BoardListItem::getBoardId).reversed());
    }
}

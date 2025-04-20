package app.slicequeue.sq_board.board.query.application.service;

import app.slicequeue.sq_board.board.query.application.dto.ReadAllByInfiniteScrollQuery;
import app.slicequeue.sq_board.board.query.application.dto.ReadAllByPageQuery;
import app.slicequeue.sq_board.board.query.infra.JpaBoardQueryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ReadAllBoardServiceTest {

    @InjectMocks
    ReadAllBoardService readAllBoardService;

    @Mock
    JpaBoardQueryRepository boardPagingQueryRepository;

    @Test
    void 페이징_조회쿼리를_통해_게시글을_복수조회한다() {
        // given
        ReadAllByPageQuery query = new ReadAllByPageQuery(1L, PageRequest.of(1, 10));

        // when
        readAllBoardService.readAll(query);

        // then
        verify(boardPagingQueryRepository, times(1))
                .findAllBoardListItemsByProjectId(query.projectId(), query.pageable());
    }

    @ParameterizedTest
    @MethodSource("invalidReadAllByPageQueries")
    void 비정상_페이징_조회쿼리를_통해_예외가발생한다(ReadAllByPageQuery query) {
        // given

        // when & then
        assertThatThrownBy(() -> readAllBoardService.readAll(query))
                .isInstanceOf(IllegalArgumentException.class);
    }

    public static Stream<Arguments> invalidReadAllByPageQueries() {
        return Stream.of(
                Arguments.of((Object) null),
                Arguments.of(new ReadAllByPageQuery(null, PageRequest.of(1, 10))),
                Arguments.of(new ReadAllByPageQuery(1L, null))
        );
    }

    void 마지막위치가_없는_무한스크롤_조회쿼리를_통해_첫위치_게시글을_복수조회한다() {
        // given
        ReadAllByInfiniteScrollQuery query = ReadAllByInfiniteScrollQuery.of(1L, 10L, null);

        // when
        readAllBoardService.readAllInfiniteScroll(query);

        // then
        verify(boardPagingQueryRepository, times(1))
                .findAllBoardListItemsInfiniteScroll(query.projectId(), query.pageSize());
    }

    void 마지막위치가_있는_무한스크롤_조회쿼리를_통해_첫위치_게시글을_복수조회한다() {
        // given
        ReadAllByInfiniteScrollQuery query = ReadAllByInfiniteScrollQuery.of(1L, 10L, "123");

        // when
        readAllBoardService.readAllInfiniteScroll(query);

        // then
        verify(boardPagingQueryRepository, times(1))
                .findAllBoardListItemsInfiniteScroll(query.projectId(), query.pageSize(), query.lastBoardId());
    }

    @ParameterizedTest
    @MethodSource("invalidReadAllByInfiniteScrollQueries")
    void 비정상_무한스크롤_조회쿼리를_통해_예외가발생한다(ReadAllByInfiniteScrollQuery query) { // given

        // when & then
        assertThatThrownBy(() -> readAllBoardService.readAllInfiniteScroll(query))
                .isInstanceOf(IllegalArgumentException.class);
    }

    public static Stream<Arguments> invalidReadAllByInfiniteScrollQueries() {
        return Stream.of(
                Arguments.of((Object) null),
                Arguments.of(ReadAllByInfiniteScrollQuery.of(null, null, null)),
                Arguments.of(ReadAllByInfiniteScrollQuery.of(1L, null, null)),
                Arguments.of(ReadAllByInfiniteScrollQuery.of(null, 10L, null))
        );
    }
}

package app.slicequeue.sq_board.board.query.application.service;

import app.slicequeue.sq_board.board.BoardTestFixture;
import app.slicequeue.sq_board.board.query.application.dto.ReadAllByPageQuery;
import app.slicequeue.sq_board.board.query.infra.JpaBoardPagingQueryRepository;
import app.slicequeue.sq_board.board.query.presentation.dto.BoardListItem;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ReadAllBoardServiceTest {

    @InjectMocks
    ReadAllBoardService readAllBoardService;

    @Mock
    JpaBoardPagingQueryRepository boardPagingQueryRepository;

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
}

package app.slicequeue.sq_board.board.query.application.service;

import app.slicequeue.common.exception.NotFoundException;
import app.slicequeue.sq_board.board.command.domain.BoardId;
import app.slicequeue.sq_board.board.query.application.dto.ReadDetailQuery;
import app.slicequeue.sq_board.board.query.infra.JpaBoardQueryRepository;
import app.slicequeue.sq_board.board.query.presentation.dto.BoardDetail;
import org.junit.jupiter.api.MethodOrderer.MethodName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@TestMethodOrder(MethodName.class)
@ExtendWith(MockitoExtension.class)
class ReadDetailBoardServiceTest {

    @InjectMocks
    ReadDetailBoardService readDetailBoardService;

    @Mock
    JpaBoardQueryRepository boardPagingQueryRepository;


    @Test
    void 상세조회쿼리를_통해_게시글을_조회한다() {
        // given
        ReadDetailQuery query = new ReadDetailQuery(1L, BoardId.from(1L));
        given(boardPagingQueryRepository.findBoardDetailBy(query.projectId(), query.boardId().getId())).willReturn(Optional.of(Mockito.mock(BoardDetail.class)));

        // when
        readDetailBoardService.readDetail(query);

        // then
        verify(boardPagingQueryRepository, times(1))
                .findBoardDetailBy(query.projectId(), query.boardId().getId());
    }

    @Test
    void 상세조회쿼리를_통해_게시글을_조회를_했으나_없는경우_NotFoundException_예외가_발생한다() {
        // given
        ReadDetailQuery query = new ReadDetailQuery(1L, BoardId.from(1L));
        given(boardPagingQueryRepository.findBoardDetailBy(query.projectId(), query.boardId().getId())).willReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> readDetailBoardService.readDetail(query)).isInstanceOf(NotFoundException.class);
    }

    @ParameterizedTest
    @MethodSource("invalidReadDetailQueries")
    void 비정상상세조회쿼리를_통해_게시글을_조회시_예외가_발생한다(ReadDetailQuery query) { // given

        // when & then
        assertThatThrownBy(() -> readDetailBoardService.readDetail(query))
                .isInstanceOf(IllegalArgumentException.class);
    }

    public static Stream<Arguments> invalidReadDetailQueries() {
        return Stream.of(
                Arguments.of((Object) null),
                Arguments.of(ReadDetailQuery.of(null, null)),
                Arguments.of(ReadDetailQuery.of(1L, null)),
                Arguments.of(ReadDetailQuery.of(null, 1L))
        );
    }
}

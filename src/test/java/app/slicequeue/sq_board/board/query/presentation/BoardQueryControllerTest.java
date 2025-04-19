package app.slicequeue.sq_board.board.query.presentation;

import app.slicequeue.sq_board.board.query.application.dto.ReadAllByInfiniteScrollQuery;
import app.slicequeue.sq_board.board.query.application.dto.ReadAllByPageQuery;
import app.slicequeue.sq_board.board.query.application.dto.ReadDetailQuery;
import app.slicequeue.sq_board.board.query.application.service.ReadAllBoardService;
import app.slicequeue.sq_board.board.query.application.service.ReadDetailBoardService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestMethodOrder(MethodOrderer.MethodName.class)
@WebMvcTest(BoardQueryController.class)
class BoardQueryControllerTest {

    @MockitoBean
    private ReadAllBoardService readAllBoardService;

    @MockitoBean
    private ReadDetailBoardService readDetailBoardService;

    @Captor
    private ArgumentCaptor<ReadAllByPageQuery> pageQueryArgumentCaptor;
    @Captor
    private ArgumentCaptor<ReadAllByInfiniteScrollQuery> infiniteScrollQueryArgumentCaptor;
    @Captor
    private ArgumentCaptor<ReadDetailQuery> detailQueryArgumentCaptor;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void 게시판페이징조회API는_정상요청이_들어올_경우_요청객체를_쿼리객체로_전환하여_프로젝트에해당하는_게시판목록_조회를_한다() throws Exception {
        // given
        String targetUrl = "/v1/boards";
        Long projectId = 1L;
        int pageNumber = 1;
        int pageSize = 5;

        // when
        mockMvc.perform(
                MockMvcRequestBuilders.get(targetUrl)
                        .queryParam("projectId", String.valueOf(projectId))
                        .queryParam("page", String.valueOf(pageNumber))
                        .queryParam("size", String.valueOf(pageSize))
        );

        // then
        verify(readAllBoardService, times(1)).readAll(pageQueryArgumentCaptor.capture());
        ReadAllByPageQuery value = pageQueryArgumentCaptor.getValue();
        assertThat(value).isNotNull();
        assertThat(value.projectId()).isEqualTo(projectId);
        assertThat(value.pageable().getPageNumber()).isEqualTo(pageNumber);
        assertThat(value.pageable().getPageSize()).isEqualTo(pageSize);
    }

    @Test
    void 게시판페이징조회API는_페이지넘버숫자없는_요청이_들어올_경우_요청객체를_디폴트페이징값이적용된_쿼리객체로_전환하여_프로젝트에해당하는_게시판목록_조회를_한다() throws Exception {
        // given
        String targetUrl = "/v1/boards";
        Long projectId = 1L;
        int defaultPageNumber = 0;
        int defaultPageSize = 10;

        // when
        mockMvc.perform(
                MockMvcRequestBuilders.get(targetUrl)
                        .queryParam("projectId", String.valueOf(projectId))
        );

        // then
        verify(readAllBoardService, times(1)).readAll(pageQueryArgumentCaptor.capture());
        ReadAllByPageQuery value = pageQueryArgumentCaptor.getValue();
        assertThat(value).isNotNull();
        assertThat(value.projectId()).isEqualTo(projectId);
        assertThat(value.pageable().getPageNumber()).isEqualTo(defaultPageNumber);
        assertThat(value.pageable().getPageSize()).isEqualTo(defaultPageSize);
    }

    @ParameterizedTest
    @MethodSource("invalidPageReadAllRequests")
    void 게시판페이징조회API는_비정상요청이_들어올_경우_400예외응답을한다(
           Long projectId, Integer pageNumber, Integer pageSize
    ) throws Exception {
        // given
        String targetUrl = "/v1/boards";

        // when
        ResultActions result = mockMvc.perform(
                MockMvcRequestBuilders.get(targetUrl)
                        .queryParam("projectId", String.valueOf(projectId))
                        .queryParam("page", String.valueOf(pageNumber))
                        .queryParam("size", String.valueOf(pageSize))
        );

        // then
        verify(readAllBoardService, times(0)).readAll(any());
        result.andDo(print())
                .andExpect(status().isBadRequest());
    }

    public static Stream<Arguments> invalidPageReadAllRequests() {
        return Stream.of(
                Arguments.of(null, null, null),
                Arguments.of(null, 1, null),
                Arguments.of(null, 1, 1),
                Arguments.of(null, null, 1)
                /* 이 케이스는 페이징 디폴트 값으로 해결됨
                Arguments.of(1L, null, null),
                Arguments.of(1L, 1, null),
                Arguments.of(1L, null, 1), */
        );
    }

    @Test
    void 게시판무한스크롤조회API는_정상요청이_들어올_경우_요청객체를_쿼리객체로_전환하여_프로젝트에해당하는_게시판목록_조회를_한다() throws Exception {
        // given
        String targetUrl = "/v1/boards/infinite-scroll";
        long projectId = 1L;
        long pageSize = 5;
        long lastBoardId = 123;

        // when
        mockMvc.perform(
                MockMvcRequestBuilders.get(targetUrl)
                        .queryParam("projectId", String.valueOf(projectId))
                        .queryParam("size", String.valueOf(pageSize))
                        .queryParam("lastBoardId", String.valueOf(lastBoardId))
        );

        // then
        verify(readAllBoardService, times(1)).readAllInfiniteScroll(infiniteScrollQueryArgumentCaptor.capture());
        ReadAllByInfiniteScrollQuery value = infiniteScrollQueryArgumentCaptor.getValue();
        assertThat(value).isNotNull();
        assertThat(value.projectId()).isEqualTo(projectId);
        assertThat(value.pageSize()).isEqualTo(pageSize);
        assertThat(value.lastBoardId()).isEqualTo(lastBoardId);
    }

    @Test
    void 게시판무한스크롤조회API는_페이지사이즈가_없는_요청이_들어올_경우_요청객체를_쿼리객체에_기본값10으로_전환하여_프로젝트에해당하는_게시판목록_조회를_한다() throws Exception {
        // given
        String targetUrl = "/v1/boards/infinite-scroll";
        long projectId = 1L;
        long expectedPageSize = 10;

        // when
        mockMvc.perform(
                MockMvcRequestBuilders.get(targetUrl)
                        .queryParam("projectId", String.valueOf(projectId))
        );

        // then
        verify(readAllBoardService, times(1)).readAllInfiniteScroll(infiniteScrollQueryArgumentCaptor.capture());
        ReadAllByInfiniteScrollQuery value = infiniteScrollQueryArgumentCaptor.getValue();
        assertThat(value).isNotNull();
        assertThat(value.projectId()).isEqualTo(projectId);
        assertThat(value.pageSize()).isEqualTo(expectedPageSize);
        assertThat(value.lastBoardId()).isNull();
    }

    @Test
    void 게시판무한스크롤조회API는_필수값이_없는_비정상_요청이_들어올_경우_400예외응답을한다() throws Exception {
        // given
        String targetUrl = "/v1/boards/infinite-scroll";

        // when
        ResultActions result = mockMvc.perform(
                MockMvcRequestBuilders.get(targetUrl)
        );

        // then
        verify(readAllBoardService, times(0)).readAllInfiniteScroll(any());
        result.andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void 게시판상세조회API는_정상요청이_들어올_경우_요청객체를_쿼리객체로_전환하여_게시판_조회를_한다() throws Exception {
        // given
        String targetUrl = "/v1/boards/{boardId}";
        long projectId = 1L;
        long boardId = 123;

        // when
        ResultActions result = mockMvc.perform(
                MockMvcRequestBuilders.get(targetUrl, String.valueOf(boardId))
                        .queryParam("projectId", String.valueOf(projectId))
        );

        // then
        result.andDo(print()).andExpect(status().isOk());
        verify(readDetailBoardService, times(1)).readDetail(detailQueryArgumentCaptor.capture());
        ReadDetailQuery value = detailQueryArgumentCaptor.getValue();
        assertThat(value).isNotNull();
        assertThat(value.projectId()).isEqualTo(projectId);
        assertThat(value.boardId().getId()).isEqualTo(boardId);
    }

    @ParameterizedTest
    @MethodSource("invalidReadDetailRequests")
    void 게시판상세조회API는_비정상요청이_들어올_경우_400응답을_한다(Long projectId, String boardId) throws Exception {
        // given
        String targetUrl = "/v1/boards/{boardId}";

        // when
        ResultActions result = mockMvc.perform(
                MockMvcRequestBuilders.get(targetUrl, String.valueOf(boardId))
                        .queryParam("projectId", String.valueOf(projectId))
        );

        // then
        result.andDo(print()).andExpect(status().isBadRequest());
        verify(readDetailBoardService, times(0)).readDetail(detailQueryArgumentCaptor.capture());
    }

    public static Stream<Arguments> invalidReadDetailRequests() {
        return Stream.of(
                Arguments.of(null, null),
                Arguments.of(null, "1"),
                Arguments.of(1L, null)
        );
    }
}

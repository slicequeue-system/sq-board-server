package app.slicequeue.sq_board.board.command.presentation;

import app.slicequeue.sq_board.board.command.application.BoardFixture;
import app.slicequeue.sq_board.board.command.application.CreateBoardService;
import app.slicequeue.sq_board.board.command.application.UpdateBoardService;
import app.slicequeue.sq_board.board.command.domain.Board;
import app.slicequeue.sq_board.board.command.domain.dto.CreateBoardCommand;
import app.slicequeue.sq_board.board.command.domain.dto.UpdateBoardCommand;
import app.slicequeue.sq_board.board.command.presentation.dto.CreateBoardRequest;
import app.slicequeue.sq_board.board.command.presentation.dto.UpdateBoardRequest;
import app.slicequeue.sq_board.common.util.DataSerializer;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BoardCommandController.class)
class BoardCommandControllerTest {

    @MockitoBean
    private CreateBoardService createBoardService;
    @MockitoBean
    private UpdateBoardService updateBoardService;

    @Captor
    private ArgumentCaptor<CreateBoardCommand> createBoardCommandArgumentCaptor;
    @Captor
    private ArgumentCaptor<UpdateBoardCommand> updateBoardCommandArgumentCaptor;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void 정상생성_API_요청이_들어올_경우_요청객체를_커맨드객체로_전환하여_게시판_생성을_한다() throws Exception {
        // given
        String targetUrl = "/v1/boards";
        Board boardExample1 = BoardFixture.createBoardExample1();
        CreateBoardRequest request = new CreateBoardRequest("게시판1 내용", 1L, 1L, "게시판1 설명 수정");

        given(createBoardService.createBoard(any())).willReturn(boardExample1.getBoardId());

        // when
        ResultActions result = mockMvc.perform(
                MockMvcRequestBuilders.post(targetUrl)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJson(request))
        );

        // then
        verify(createBoardService, times(1)).createBoard(createBoardCommandArgumentCaptor.capture());
        CreateBoardCommand value = createBoardCommandArgumentCaptor.getValue();

        assertThat(value.name()).isEqualTo(request.getName());
        assertThat(value.projectId()).isEqualTo(request.getProjectId());
        assertThat(value.adminId()).isEqualTo(request.getAdminId());
        assertThat(value.description()).isEqualTo(request.getDescription());

        result.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isNumber());
    }

    @Test
    void 정상수정_API_요청이_들어올_경우_요청객체를_커맨드객체로_전환하여_게시판_수정을_한다() throws Exception {
        // given
        String targetUrl = "/v1/boards/{boardId}";
        Board boardExample1 = BoardFixture.createBoardExample1();
        UpdateBoardRequest request = new UpdateBoardRequest("게시판1 내용 수정", null, 1L, "게시판1 설명 수정");

        given(updateBoardService.updateBoard(any())).willReturn(boardExample1.getBoardId());

        // when
        ResultActions result = mockMvc.perform(
                MockMvcRequestBuilders.put(targetUrl, boardExample1.getBoardId().getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJson(request))
        );

        // then
        verify(updateBoardService, times(1)).updateBoard(updateBoardCommandArgumentCaptor.capture());
        UpdateBoardCommand value = updateBoardCommandArgumentCaptor.getValue();

        assertThat(value.boardId()).isEqualTo(boardExample1.getBoardId());
        assertThat(value.name()).isEqualTo(request.getName());
        assertThat(value.adminId()).isEqualTo(request.getAdminId());
        assertThat(value.description()).isEqualTo(request.getDescription());

        result.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isNumber());
    }

    private static String asJson(Object request) {
        String json = DataSerializer.serialize(request);
        if (json == null) {
            throw new RuntimeException("[BoardCommandControllerTest::asJson] 직렬화 관련 오류 발생 - data: %s".formatted(request.toString()));
        }
        return json;
    }
}

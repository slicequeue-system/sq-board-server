package app.slicequeue.sq_board.board.command.application;

import app.slicequeue.sq_board.board.command.domain.Board;
import app.slicequeue.sq_board.board.command.domain.BoardRepository;
import app.slicequeue.sq_board.board.command.dto.CreateBoardRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateBoardServiceTest {

    @InjectMocks
    CreateBoardService createBoardService;

    @Mock
    BoardRepository boardRepository;

    @Captor
    ArgumentCaptor<Board> boardArgumentCaptor;

    @Test
    void 유효한_요청으로_게시판_생성시_정상적으로_저장된다() { // TODO 테스트 픽스쳐 가져가 보기로! - 수정과 더불어 좋은 예시 참고하여!
        // given
        CreateBoardRequest request = new CreateBoardRequest("게시판1", 1L, 1L, "설명");
        given(boardRepository.save(any())).willReturn(mock(Board.class));

        // when
        createBoardService.createBoard(request);


        // then
        verify(boardRepository, times(1)).save(boardArgumentCaptor.capture());
        Board value = boardArgumentCaptor.getValue();
        assertThat(value).isNotNull();
        assertThat(value.getBoardId()).isNotNull();
        assertThat(value.getName()).isEqualTo(request.name());
        assertThat(value.getProjectId()).isEqualTo(request.projectId());
        assertThat(value.getAdminId()).isEqualTo(request.adminId());
        assertThat(value.getDescription()).isEqualTo(request.description());
    }
}

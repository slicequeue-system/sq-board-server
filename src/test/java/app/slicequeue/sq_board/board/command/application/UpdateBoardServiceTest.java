package app.slicequeue.sq_board.board.command.application;

import app.slicequeue.common.exception.BadRequestException;
import app.slicequeue.common.exception.NotFoundException;
import app.slicequeue.sq_board.board.command.domain.Board;
import app.slicequeue.sq_board.board.BoardTestFixture;
import app.slicequeue.sq_board.board.command.domain.BoardRepository;
import app.slicequeue.sq_board.board.command.domain.dto.UpdateBoardCommand;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UpdateBoardServiceTest {

    @InjectMocks
    UpdateBoardService updateBoardService;
    @Mock
    BoardRepository boardRepository;

    @Captor
    ArgumentCaptor<Board> boardArgumentCaptor;

    @Test
    void 유효한_수정커멘드로_게시판_수정시_정상적으로_저장된다() {
        // given`
        Board board = BoardTestFixture.createBoardExample1();
        UpdateBoardCommand command = new UpdateBoardCommand(board.getBoardId(), "게시글1제목수정", 100L, "게시글1설명내용수정");

        given(boardRepository.findByBoardId(board.getBoardId())).willReturn(Optional.of(board));
        given(boardRepository.save(any())).willReturn(Mockito.mock(Board.class));

        // when
        updateBoardService.updateBoard(command);

        // then
        verify(boardRepository, times(1)).save(boardArgumentCaptor.capture());
    }

    @Test
    void 메서드_호출시_인자로_잘못된_값_예외가_발생시_잘못된_예외로_되던진다() {
        // given
        Board board = BoardTestFixture.createBoardExample1();
        UpdateBoardCommand command = new UpdateBoardCommand(board.getBoardId(), "게시글1제목수정", null, "게시글1설명내용수정");

        given(boardRepository.findByBoardId(board.getBoardId())).willReturn(Optional.of(board));

        // when & then
        assertThatThrownBy(() ->  updateBoardService.updateBoard(command)).isInstanceOf(BadRequestException.class);
    }

    @Test
    void 메서드_호출시_인자로_잘못된_값_예외가_아닌_다른예외_발생시_그대로_되던진다() {
        // given
        Board board = BoardTestFixture.createBoardExample1();
        UpdateBoardCommand command = new UpdateBoardCommand(board.getBoardId(), "게시글1제목수정", null, "게시글1설명내용수정");

        given(boardRepository.findByBoardId(board.getBoardId())).willReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() ->  updateBoardService.updateBoard(command)).isInstanceOf(NotFoundException.class);
    }

}

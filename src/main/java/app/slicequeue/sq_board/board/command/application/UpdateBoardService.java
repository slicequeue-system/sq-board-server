package app.slicequeue.sq_board.board.command.application;

import app.slicequeue.common.exception.BadRequestException;
import app.slicequeue.common.exception.NotFoundException;
import app.slicequeue.sq_board.board.command.domain.Board;
import app.slicequeue.sq_board.board.command.domain.BoardId;
import app.slicequeue.sq_board.board.command.domain.BoardRepository;
import app.slicequeue.sq_board.board.command.domain.dto.CreateBoardCommand;
import app.slicequeue.sq_board.board.command.domain.dto.UpdateBoardCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UpdateBoardService {

    private final BoardRepository boardRepository;

    public BoardId updateBoard(UpdateBoardCommand command) {
        try {
            Board board =
                    boardRepository.findByBoardId(command.boardId())
                            .orElseThrow(() -> new NotFoundException(command.boardId().toString()));

            board.update(command);
            return boardRepository.save(board).getBoardId();
        } catch (IllegalArgumentException e) {
            throw new BadRequestException(e.getMessage());
        }
    }

}

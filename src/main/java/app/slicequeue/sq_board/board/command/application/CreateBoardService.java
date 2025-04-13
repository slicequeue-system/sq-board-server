package app.slicequeue.sq_board.board.command.application;

import app.slicequeue.common.exception.BadRequestException;
import app.slicequeue.sq_board.board.command.domain.Board;
import app.slicequeue.sq_board.board.command.domain.BoardId;
import app.slicequeue.sq_board.board.command.domain.BoardRepository;
import app.slicequeue.sq_board.board.command.domain.dto.CreateBoardCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CreateBoardService {

    private final BoardRepository boardRepository;

    public BoardId createBoard(CreateBoardCommand command) {
        try {
            Board board = Board.create(command);
            return boardRepository.save(board).getBoardId();
        } catch (IllegalArgumentException e) {
            throw new BadRequestException(e.getMessage());
        }
    }

}

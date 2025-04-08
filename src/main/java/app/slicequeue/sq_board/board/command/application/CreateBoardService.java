package app.slicequeue.sq_board.board.command.application;

import app.slicequeue.sq_board.board.command.domain.Board;
import app.slicequeue.sq_board.board.command.domain.BoardId;
import app.slicequeue.sq_board.board.command.domain.BoardRepository;
import app.slicequeue.sq_board.board.command.dto.CreateBoardRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CreateBoardService {

    private final BoardRepository boardRepository;

    public BoardId createBoard(CreateBoardRequest request) {
        return boardRepository.save(Board.create(request)).getBoardId();
    }

}

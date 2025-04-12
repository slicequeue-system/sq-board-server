package app.slicequeue.sq_board.board.command.presentation;

import app.slicequeue.sq_board.board.command.application.CreateBoardService;
import app.slicequeue.sq_board.board.command.domain.BoardId;
import app.slicequeue.sq_board.board.command.domain.dto.CreateBoardRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/boards")
@RequiredArgsConstructor
public class BoardCommandController {

    private final CreateBoardService createBoardService;

    @PostMapping
    public BoardId create(@RequestBody CreateBoardRequest request) {
        return createBoardService.createBoard(request);
    }

}

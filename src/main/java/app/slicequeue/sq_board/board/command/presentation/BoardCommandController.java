package app.slicequeue.sq_board.board.command.presentation;

import app.slicequeue.common.dto.CommonResponse;
import app.slicequeue.sq_board.board.command.application.CreateBoardService;
import app.slicequeue.sq_board.board.command.domain.BoardId;
import app.slicequeue.sq_board.board.command.domain.dto.CreateBoardCommand;
import app.slicequeue.sq_board.board.command.presentation.dto.CreateBoardRequest;
import jakarta.validation.Valid;
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
    public CommonResponse<BoardId> create(@RequestBody @Valid CreateBoardRequest request) {
        CreateBoardCommand command = CreateBoardCommand.from(request);
        return CommonResponse.success(createBoardService.createBoard(command));
    }

}

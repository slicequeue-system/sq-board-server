package app.slicequeue.sq_board.board.command.presentation;

import app.slicequeue.common.dto.CommonResponse;
import app.slicequeue.sq_board.board.command.application.CreateBoardService;
import app.slicequeue.sq_board.board.command.application.UpdateBoardService;
import app.slicequeue.sq_board.board.command.domain.dto.CreateBoardCommand;
import app.slicequeue.sq_board.board.command.domain.dto.UpdateBoardCommand;
import app.slicequeue.sq_board.board.command.presentation.dto.CreateBoardRequest;
import app.slicequeue.sq_board.board.command.presentation.dto.UpdateBoardRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/boards")
@RequiredArgsConstructor
public class BoardCommandController {

    private final CreateBoardService createBoardService;
    private final UpdateBoardService updateBoardService;

    @PostMapping
    public CommonResponse<String> create(@RequestBody @Valid CreateBoardRequest request) {
        CreateBoardCommand command = CreateBoardCommand.from(request);
        return CommonResponse.success("created.", createBoardService.createBoard(command).toString());
    }

    @PutMapping("/{boardId}")
    public CommonResponse<String> update(@PathVariable("boardId") Long boardId,
                                       @RequestBody @Valid UpdateBoardRequest request) {
        UpdateBoardCommand command = UpdateBoardCommand.of(boardId, request);
        return CommonResponse.success("updated.", updateBoardService.updateBoard(command).toString());
    }

}

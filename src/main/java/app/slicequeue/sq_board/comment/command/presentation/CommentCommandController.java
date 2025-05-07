package app.slicequeue.sq_board.comment.command.presentation;

import app.slicequeue.common.dto.CommonResponse;
import app.slicequeue.sq_board.comment.command.application.usecase.CreateCommentUseCase;
import app.slicequeue.sq_board.comment.command.application.usecase.DeleteCommentUseCase;
import app.slicequeue.sq_board.comment.command.application.usecase.UpdateCommentUseCase;
import app.slicequeue.sq_board.comment.command.domain.CommentId;
import app.slicequeue.sq_board.comment.command.domain.dto.CreateCommentCommand;
import app.slicequeue.sq_board.comment.command.domain.dto.DeleteCommentCommand;
import app.slicequeue.sq_board.comment.command.domain.dto.UpdateCommentCommand;
import app.slicequeue.sq_board.comment.command.presentation.dto.CreateCommentRequest;
import app.slicequeue.sq_board.comment.command.presentation.dto.UpdateCommentRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/comments")
@RequiredArgsConstructor
public class CommentCommandController {

    private final CreateCommentUseCase createCommentUseCase;
    private final UpdateCommentUseCase updateCommentUseCase;
    private final DeleteCommentUseCase deleteCommentUseCase;

    @PostMapping
    public CommonResponse<String> create(@RequestBody @Valid CreateCommentRequest request) {
        CreateCommentCommand command = CreateCommentCommand.from(request);
        return CommonResponse.success("created", createCommentUseCase.execute(command).toString());
    }

    @PutMapping("/{commentId}")
    public CommonResponse<String> update(@PathVariable("commentId") Long commentId,
                                         @RequestBody @Valid UpdateCommentRequest request) {
        UpdateCommentCommand command = UpdateCommentCommand.of(CommentId.from(commentId), request);
        return CommonResponse.success("updated", updateCommentUseCase.execute(command).toString());
    }

    @DeleteMapping("/{commentId}")
    public CommonResponse<String> delete(@PathVariable("commentId") Long commentId) {
        DeleteCommentCommand command = DeleteCommentCommand.from(CommentId.from(commentId));
        return CommonResponse.success("updated", deleteCommentUseCase.execute(command).toString());
    }

}

package app.slicequeue.sq_board.comment.command.presentation;

import app.slicequeue.common.dto.CommonResponse;
import app.slicequeue.sq_board.comment.command.application.CreateCommentService;
import app.slicequeue.sq_board.comment.command.application.UpdateCommentService;
import app.slicequeue.sq_board.comment.command.domain.CommentId;
import app.slicequeue.sq_board.comment.command.domain.dto.CreateCommentCommand;
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

    private final CreateCommentService createCommentService;
    private final UpdateCommentService updateCommentService;

    @PostMapping
    public CommonResponse<String> create(@RequestBody @Valid CreateCommentRequest request) {
        CreateCommentCommand command = CreateCommentCommand.from(request);
        return CommonResponse.success("created", createCommentService.createComment(command).toString());
    }

    @PutMapping("/{commentId}")
    public CommonResponse<String> update(@PathVariable("commentId") Long commentId,
                                         @RequestBody @Valid UpdateCommentRequest request) {
        UpdateCommentCommand command = UpdateCommentCommand.of(CommentId.from(commentId), request);
        return CommonResponse.success("updated", updateCommentService.updateComment(command).toString());
    }

}

package app.slicequeue.sq_board.comment.command.presentation;

import app.slicequeue.common.dto.CommonResponse;
import app.slicequeue.sq_board.comment.command.application.CreateCommentService;
import app.slicequeue.sq_board.comment.command.domain.dto.CreateCommentCommand;
import app.slicequeue.sq_board.comment.command.presentation.dto.CreateCommentRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/comments")
@RequiredArgsConstructor
public class CommentCommandController {

    private final CreateCommentService createCommentService;

    @PostMapping
    public CommonResponse<String> create(@RequestBody @Valid CreateCommentRequest request) {
        CreateCommentCommand command = CreateCommentCommand.from(request);
        return CommonResponse.success("created", createCommentService.createComment(command).toString());
    }

}

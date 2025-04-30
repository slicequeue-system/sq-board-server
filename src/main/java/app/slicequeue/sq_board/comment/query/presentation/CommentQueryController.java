package app.slicequeue.sq_board.comment.query.presentation;

import app.slicequeue.common.dto.CommonResponse;
import app.slicequeue.sq_board.comment.query.application.dto.ReadCommentDetailQuery;
import app.slicequeue.sq_board.comment.query.application.service.ReadCommentService;
import app.slicequeue.sq_board.comment.query.presentation.dto.CommentDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/comments")
@RequiredArgsConstructor
public class CommentQueryController {

    private final ReadCommentService readCommentService;

    @GetMapping("/{commentId}")
    public CommonResponse<CommentDetail> read(@PathVariable("commentId") Long id) {
        ReadCommentDetailQuery query = ReadCommentDetailQuery.from(id);
        return CommonResponse.success(readCommentService.read(query));
    }

}

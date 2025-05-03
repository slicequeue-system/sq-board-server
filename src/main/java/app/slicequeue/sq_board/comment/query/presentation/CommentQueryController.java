package app.slicequeue.sq_board.comment.query.presentation;

import app.slicequeue.common.dto.CommonResponse;
import app.slicequeue.sq_board.comment.query.application.dto.ReadAllCommentsPageQuery;
import app.slicequeue.sq_board.comment.query.application.dto.ReadCommentDetailQuery;
import app.slicequeue.sq_board.comment.query.application.service.ReadCommentService;
import app.slicequeue.sq_board.comment.query.presentation.dto.CommentDetail;
import app.slicequeue.sq_board.comment.query.presentation.dto.PageResponse;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/comments")
@RequiredArgsConstructor
public class CommentQueryController {

    private final ReadCommentService readCommentService;

    @GetMapping
    public CommonResponse<PageResponse<CommentDetail>> readAll(
            @RequestParam(value = "articleId") long articleId,
            @RequestParam(value = "page", defaultValue = "1", required = false)
            @Positive(message = "page must be positive.") long page,
            @RequestParam(value = "size", defaultValue = "15", required = false)
            @Positive(message = "size must be positive.") int size,
            @RequestParam(value = "movablePageCount", defaultValue = "5", required = false)
            @Positive(message = "movablePageCount must be positive.") int movablePageCount) {
        ReadAllCommentsPageQuery query = ReadAllCommentsPageQuery.of(
                articleId, page, size, movablePageCount);
        PageResponse<CommentDetail> response = readCommentService.readAll(query);
        return CommonResponse.success(response);
    }

    @GetMapping("/{commentId}")
    public CommonResponse<CommentDetail> read(@PathVariable("commentId") Long id) {
        ReadCommentDetailQuery query = ReadCommentDetailQuery.from(id);
        return CommonResponse.success(readCommentService.read(query));
    }

}

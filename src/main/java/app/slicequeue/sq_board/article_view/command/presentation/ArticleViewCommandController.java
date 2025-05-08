package app.slicequeue.sq_board.article_view.command.presentation;

import app.slicequeue.common.dto.CommonResponse;
import app.slicequeue.sq_board.article.command.domain.ArticleId;
import app.slicequeue.sq_board.article_view.command.application.ArticleViewService;
import app.slicequeue.sq_board.article_view.command.domain.dto.IncreaseArticleViewCountCommand;
import app.slicequeue.sq_board.article_view.command.presentation.dto.IncreaseArticleViewCountRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/articles/views")
@RequiredArgsConstructor
public class ArticleViewCommandController {
    private final ArticleViewService articleViewService;

    @PostMapping
    public CommonResponse<Long> increase(
            @RequestBody @Valid IncreaseArticleViewCountRequest request) {
        IncreaseArticleViewCountCommand command = IncreaseArticleViewCountCommand.from(request);
        return CommonResponse.success(articleViewService.increase(command));
    }

    @GetMapping
    public CommonResponse<Long> count(
            @RequestParam("articleId") @NotNull(message = "게시글 식별값이 없습니다.") Long articleId) {
        return CommonResponse.success(articleViewService.count(ArticleId.from(articleId)));
    }
}

package app.slicequeue.sq_board.article.command.presentation;

import app.slicequeue.common.dto.CommonResponse;
import app.slicequeue.sq_board.article.command.application.CreateArticleService;
import app.slicequeue.sq_board.article.command.application.UpdateArticleService;
import app.slicequeue.sq_board.article.command.domain.ArticleId;
import app.slicequeue.sq_board.article.command.domain.dto.CreateArticleCommand;
import app.slicequeue.sq_board.article.command.domain.dto.UpdateArticleCommand;
import app.slicequeue.sq_board.article.command.presentation.dto.CreateArticleRequest;
import app.slicequeue.sq_board.article.command.presentation.dto.UpdateArticleRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/articles")
@RequiredArgsConstructor
public class ArticleCommandController {

    private final CreateArticleService createArticleService;
    private final UpdateArticleService updateArticleService;

    @PostMapping
    public CommonResponse<String> create(@RequestBody @Valid CreateArticleRequest request) {
        CreateArticleCommand command = CreateArticleCommand.from(request);
        return CommonResponse.success("created", createArticleService.createArticle(command).toString());
    }

    @PutMapping("/{articleId}")
    public CommonResponse<String> update(@PathVariable("articleId") Long articleId,
                                         @RequestBody @Valid UpdateArticleRequest updateArticleRequest) {
        UpdateArticleCommand command = UpdateArticleCommand.from(ArticleId.from(articleId), updateArticleRequest);
        return CommonResponse.success("updated", updateArticleService.updateArticle(command).toString());
    }

}

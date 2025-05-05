package app.slicequeue.sq_board.article.command.presentation;

import app.slicequeue.common.dto.CommonResponse;
import app.slicequeue.sq_board.article.command.application.*;
import app.slicequeue.sq_board.article.command.domain.ArticleId;
import app.slicequeue.sq_board.article.command.domain.dto.CreateArticleCommand;
import app.slicequeue.sq_board.article.command.domain.dto.DeleteArticleCommand;
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

    private final CreateArticleUseCase createArticleUseCase;
    private final UpdateArticleService updateArticleService;
    private final DeleteArticleUseCase deleteArticleUseCase;

    @PostMapping
    public CommonResponse<String> create(@RequestBody @Valid CreateArticleRequest request) {
        CreateArticleCommand command = CreateArticleCommand.from(request);
        return CommonResponse.success("created", createArticleUseCase.execute(command).toString());
    }

    @PutMapping("/{articleId}")
    public CommonResponse<String> update(@PathVariable("articleId") Long articleId,
                                         @RequestBody @Valid UpdateArticleRequest updateArticleRequest) {
        UpdateArticleCommand command = UpdateArticleCommand.from(ArticleId.from(articleId), updateArticleRequest);
        return CommonResponse.success("updated", updateArticleService.updateArticle(command).toString());
    }

    @DeleteMapping("/{articleId}")
    public CommonResponse<String> delete(@PathVariable("articleId") Long articleId) {
        DeleteArticleCommand command = DeleteArticleCommand.from(articleId);
        return CommonResponse.success("deleted", deleteArticleUseCase.execute(command).toString());
    }

}

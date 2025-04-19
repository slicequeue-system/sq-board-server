package app.slicequeue.sq_board.article.command.presentation;

import app.slicequeue.common.dto.CommonResponse;
import app.slicequeue.sq_board.article.command.application.CreateArticleService;
import app.slicequeue.sq_board.article.command.domain.dto.CreateArticleCommand;
import app.slicequeue.sq_board.article.command.presentation.dto.CreateArticleRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/articles")
@RequiredArgsConstructor
public class ArticleCommandController {

    private final CreateArticleService createArticleService;

    @PostMapping
    public CommonResponse<String> create(@RequestBody @Valid CreateArticleRequest request) {
        CreateArticleCommand command = CreateArticleCommand.from(request);
        return CommonResponse.success("created", createArticleService.createArticle(command).toString());
    }

}

package app.slicequeue.sq_board.article_reaction.command.presentation;

import app.slicequeue.common.dto.CommonResponse;
import app.slicequeue.sq_board.article_reaction.command.application.CreateArticleReactionUseCase;
import app.slicequeue.sq_board.article_reaction.command.domain.dto.CreateArticleReactionCommand;
import app.slicequeue.sq_board.article_reaction.command.presentation.dto.CreateArticleReactionRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/articles/reactions")
@RequiredArgsConstructor
public class ArticleReactionCommandController {

    private final CreateArticleReactionUseCase createArticleReactionUseCase;

    @PostMapping
    public CommonResponse<String> create(CreateArticleReactionRequest request) {
        CreateArticleReactionCommand command = CreateArticleReactionCommand.from(request);
        createArticleReactionUseCase.execute(command);
        return CommonResponse.success();
    }
}


package app.slicequeue.sq_board.article_reaction.command.presentation;

import app.slicequeue.common.dto.CommonResponse;
import app.slicequeue.sq_board.article_reaction.command.application.ToggleArticleReactionUseCase;
import app.slicequeue.sq_board.article_reaction.command.application.ToggleArticleReactionUseCase.ReactionToggleResult;
import app.slicequeue.sq_board.article_reaction.command.domain.dto.ArticleReactionCommand;
import app.slicequeue.sq_board.article_reaction.command.presentation.dto.ToggleArticleReactionRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/articles/reactions")
@RequiredArgsConstructor
public class ArticleReactionCommandController {

    private final ToggleArticleReactionUseCase toggleArticleReactionUseCase;

    @PostMapping
    public CommonResponse<String> toggle(@RequestBody @Valid ToggleArticleReactionRequest request) {
        ArticleReactionCommand command = ArticleReactionCommand.from(request);
        ReactionToggleResult toggleResult = toggleArticleReactionUseCase.execute(command);
        return CommonResponse.success(toggleResult.name());
    }
}


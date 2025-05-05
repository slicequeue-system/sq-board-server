package app.slicequeue.sq_board.comment_reaction.command.presentation;

import app.slicequeue.common.dto.CommonResponse;
import app.slicequeue.sq_board.article_reaction.command.application.ToggleArticleReactionUseCase;
import app.slicequeue.sq_board.article_reaction.command.application.ToggleArticleReactionUseCase.ReactionToggleResult;
import app.slicequeue.sq_board.article_reaction.command.domain.dto.ArticleReactionCommand;
import app.slicequeue.sq_board.article_reaction.command.presentation.dto.ToggleArticleReactionRequest;
import app.slicequeue.sq_board.comment_reaction.command.application.usecase.ToggleCommentReactionUseCase;
import app.slicequeue.sq_board.comment_reaction.command.domain.dto.CommentReactionCommand;
import app.slicequeue.sq_board.comment_reaction.command.presentation.dto.ToggleCommentReactionRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/comments/reactions")
@RequiredArgsConstructor
public class CommentReactionCommandController {

    private final ToggleCommentReactionUseCase toggleCommentReactionUseCase;


    @PostMapping
    public CommonResponse<String> toggle(@RequestBody @Valid ToggleCommentReactionRequest request) {
        CommentReactionCommand command = CommentReactionCommand.from(request);
        return CommonResponse.success(toggleCommentReactionUseCase.execute(command).name());
    }
}


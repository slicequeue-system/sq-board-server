package app.slicequeue.sq_board.article_reaction.command.application;

import app.slicequeue.sq_board.article_reaction.command.domain.ArticleReaction;
import app.slicequeue.sq_board.article_reaction.command.domain.ArticleReactionId;
import app.slicequeue.sq_board.article_reaction.command.domain.dto.CreateArticleReactionCommand;
import app.slicequeue.sq_board.article_reaction.command.domain.dto.IncreaseArticleReactionCountCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreateArticleReactionUseCase {

    private final CreateArticleReactionService createArticleReactionService;
    private final SummarizeArticleReactionCountService summarizeArticleReactionCountService;

    @Transactional
    public ArticleReactionId execute(CreateArticleReactionCommand command) {
        ArticleReaction created = createArticleReactionService.create(command);
        summarizeArticleReactionCountService.increase(IncreaseArticleReactionCountCommand.from(created));
        return created.getArticleReactionId();
    }
}

package app.slicequeue.sq_board.article_reaction.command.application;

import app.slicequeue.sq_board.article_reaction.command.domain.ArticleReaction;
import app.slicequeue.sq_board.article_reaction.command.domain.ArticleReactionRepository;
import app.slicequeue.sq_board.article_reaction.command.domain.dto.ArticleReactionCommand;
import app.slicequeue.sq_board.article_reaction.command.domain.dto.ArticleReactionCountCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ToggleArticleReactionUseCase {

    private final CreateArticleReactionService createArticleReactionService;
    private final DeleteArticleReactionService deleteArticleReactionService;
    private final SummarizeArticleReactionCountService summarizeArticleReactionCountService;

    private final ArticleReactionRepository articleReactionRepository;

    public enum ReactionToggleResult { ADDED, REMOVED }

    @Transactional
    public ReactionToggleResult execute(ArticleReactionCommand command) {
        Optional<ArticleReaction> reactionOptional = articleReactionRepository.findByArticleIdAndEmojiAndUserId(
                command.articleId(), command.emoji(), command.userId());

        if (reactionOptional.isEmpty()) {
            summarizeArticleReactionCountService.increaseWithLock(
                    ArticleReactionCountCommand.from(command),
                    (count) -> createArticleReactionService.create(command)
            );
            return ReactionToggleResult.ADDED;
        } else {
            summarizeArticleReactionCountService.decreaseWithLock(
                    ArticleReactionCountCommand.from(command),
                    (count) -> deleteArticleReactionService.delete(reactionOptional.get())
            );
            return ReactionToggleResult.REMOVED;
        }
    }
}

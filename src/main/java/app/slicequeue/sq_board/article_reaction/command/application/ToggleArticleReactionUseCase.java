package app.slicequeue.sq_board.article_reaction.command.application;

import app.slicequeue.common.exception.NotFoundException;
import app.slicequeue.sq_board.article_reaction.command.domain.ArticleReaction;
import app.slicequeue.sq_board.article_reaction.command.domain.ArticleReactionRepository;
import app.slicequeue.sq_board.article_reaction.command.domain.dto.ArticleReactionCommand;
import app.slicequeue.sq_board.article_reaction.command.domain.dto.ArticleReactionCountCommand;
import app.slicequeue.sq_board.common.util.ConstraintViolationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
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

    public enum ReactionToggleResult {ADDED, REMOVED}

    public ReactionToggleResult execute(ArticleReactionCommand command) {
        try {
            return executeWithTransaction(command);
        } catch (DataIntegrityViolationException e) {
            if (ConstraintViolationUtils.isForeignKeyViolation(e)) {
                throw new NotFoundException("존재하지 않는 게시글 입니다.");
            }
            throw e;
        }
    }

    @Transactional
    public ReactionToggleResult executeWithTransaction(ArticleReactionCommand command) {
        Optional<ArticleReaction> reactionOptional = articleReactionRepository.findByArticleIdAndEmojiAndUserId(
                command.articleId(), command.emoji(), command.userId());

        if (reactionOptional.isEmpty()) {
            summarizeArticleReactionCountService.increaseWithLock(
                    ArticleReactionCountCommand.from(command),
                    () -> createArticleReactionService.create(command)
            );
            return ReactionToggleResult.ADDED;
        } else {
            summarizeArticleReactionCountService.decreaseWithLock(
                    ArticleReactionCountCommand.from(command),
                    () -> deleteArticleReactionService.delete(reactionOptional.get())
            );
            return ReactionToggleResult.REMOVED;
        }
    }


}

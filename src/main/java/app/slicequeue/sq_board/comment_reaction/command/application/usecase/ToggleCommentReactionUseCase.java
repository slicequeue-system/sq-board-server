package app.slicequeue.sq_board.comment_reaction.command.application.usecase;

import app.slicequeue.common.exception.NotFoundException;
import app.slicequeue.sq_board.comment_reaction.command.application.service.CreateCommentReactionService;
import app.slicequeue.sq_board.comment_reaction.command.application.service.DeleteCommentReactionService;
import app.slicequeue.sq_board.comment_reaction.command.application.service.SummarizeCommentReactionCountService;
import app.slicequeue.sq_board.comment_reaction.command.domain.CommentReaction;
import app.slicequeue.sq_board.comment_reaction.command.domain.CommentReactionRepository;
import app.slicequeue.sq_board.comment_reaction.command.domain.dto.CommentReactionCommand;
import app.slicequeue.sq_board.comment_reaction.command.domain.dto.CommentReactionCountCommand;
import app.slicequeue.sq_board.common.util.ConstraintViolationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ToggleCommentReactionUseCase {

    private final CreateCommentReactionService createCommentReactionService;
    private final DeleteCommentReactionService deleteCommentReactionService;
    private final SummarizeCommentReactionCountService summarizeCommentReactionCountService;

    private final CommentReactionRepository commentReactionRepository;

    public enum ReactionToggleResult {ADDED, REMOVED}

    public ReactionToggleResult execute(CommentReactionCommand command) {
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
    public ReactionToggleResult executeWithTransaction(CommentReactionCommand command) {
        Optional<CommentReaction> reactionOptional = commentReactionRepository.findByCommentIdAndEmojiAndUserId(
                command.commentId(), command.emoji(), command.userId());

        if (reactionOptional.isEmpty()) {
            summarizeCommentReactionCountService.increaseWithLock(
                    CommentReactionCountCommand.from(command),
                    () -> createCommentReactionService.create(command)
            );
            return ReactionToggleResult.ADDED;
        } else {
            summarizeCommentReactionCountService.decreaseWithLock(
                    CommentReactionCountCommand.from(command),
                    () -> deleteCommentReactionService.delete(reactionOptional.get())
            );
            return ReactionToggleResult.REMOVED;
        }
    }


}

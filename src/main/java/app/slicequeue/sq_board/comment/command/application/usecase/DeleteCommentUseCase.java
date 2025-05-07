package app.slicequeue.sq_board.comment.command.application.usecase;

import app.slicequeue.common.exception.NotFoundException;
import app.slicequeue.sq_board.comment.command.application.service.DeleteCommentService;
import app.slicequeue.sq_board.comment.command.application.service.SummarizeArticleCommentCountService;
import app.slicequeue.sq_board.comment.command.domain.Comment;
import app.slicequeue.sq_board.comment.command.domain.CommentId;
import app.slicequeue.sq_board.comment.command.domain.dto.ArticleCommentCountCommand;
import app.slicequeue.sq_board.comment.command.domain.dto.CreateCommentCommand;
import app.slicequeue.sq_board.comment.command.domain.dto.DeleteCommentCommand;
import app.slicequeue.sq_board.common.util.ConstraintViolationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
public class DeleteCommentUseCase {

    private final DeleteCommentService deleteCommentService;
    private final SummarizeArticleCommentCountService summarizeArticleCommentCountService;

    public CommentId execute(DeleteCommentCommand command) {
        try {
            return executeWithTransaction(command);
        } catch (
                DataIntegrityViolationException e) {
            if (ConstraintViolationUtils.isForeignKeyViolation(e)) {
                throw new NotFoundException("존재하지 않는 게시글입니다.");
            }
            throw e;
        }
    }

    @Transactional
    private CommentId executeWithTransaction(DeleteCommentCommand command) {
        AtomicReference<Comment> comment = new AtomicReference<>();
        summarizeArticleCommentCountService.decreaseWithLock(
                () -> {
                    Comment deleted = deleteCommentService.deleteComment(command);
                    comment.set(deleted);
                    return deleted;
                });
        return comment.get().getCommentId();
    }

}

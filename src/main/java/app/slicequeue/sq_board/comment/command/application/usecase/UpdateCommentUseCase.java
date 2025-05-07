package app.slicequeue.sq_board.comment.command.application.usecase;

import app.slicequeue.common.exception.NotFoundException;
import app.slicequeue.sq_board.comment.command.application.service.UpdateCommentService;
import app.slicequeue.sq_board.comment.command.domain.Comment;
import app.slicequeue.sq_board.comment.command.domain.CommentId;
import app.slicequeue.sq_board.comment.command.domain.dto.ArticleCommentCountCommand;
import app.slicequeue.sq_board.comment.command.domain.dto.CreateCommentCommand;
import app.slicequeue.sq_board.comment.command.domain.dto.UpdateCommentCommand;
import app.slicequeue.sq_board.common.util.ConstraintViolationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
public class UpdateCommentUseCase {

    private final UpdateCommentService updateCommentService;

    public CommentId execute(UpdateCommentCommand command) {
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
    private CommentId executeWithTransaction(UpdateCommentCommand command) {
        Comment comment = updateCommentService.updateComment(command);
        return comment.getCommentId();
    }

}

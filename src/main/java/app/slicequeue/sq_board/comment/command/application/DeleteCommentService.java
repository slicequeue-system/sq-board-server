package app.slicequeue.sq_board.comment.command.application;

import app.slicequeue.common.exception.NotFoundException;
import app.slicequeue.sq_board.comment.command.domain.Comment;
import app.slicequeue.sq_board.comment.command.domain.CommentId;
import app.slicequeue.sq_board.comment.command.domain.CommentRepository;
import app.slicequeue.sq_board.comment.command.domain.dto.DeleteCommentCommand;
import app.slicequeue.sq_board.comment.command.domain.dto.UpdateCommentCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class DeleteCommentService {

    private final CommentRepository commentRepository;

    public CommentId deleteComment(DeleteCommentCommand command) {
        Comment comment = commentRepository.findByCommentId(command.commentId())
                .orElseThrow(() -> new NotFoundException("댓글을 찾을 수가 없습니다."));
        comment.delete();
        return commentRepository.save(comment).getCommentId();
    }
}

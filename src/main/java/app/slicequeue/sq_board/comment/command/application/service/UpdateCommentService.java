package app.slicequeue.sq_board.comment.command.application.service;

import app.slicequeue.common.exception.NotFoundException;
import app.slicequeue.sq_board.comment.command.domain.Comment;
import app.slicequeue.sq_board.comment.command.domain.CommentId;
import app.slicequeue.sq_board.comment.command.domain.CommentPath;
import app.slicequeue.sq_board.comment.command.domain.CommentRepository;
import app.slicequeue.sq_board.comment.command.domain.dto.CreateCommentCommand;
import app.slicequeue.sq_board.comment.command.domain.dto.UpdateCommentCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UpdateCommentService {

    private final CommentRepository commentRepository;

    public Comment updateComment(UpdateCommentCommand command) {
        Comment comment = commentRepository.findByCommentId(command.commentId())
                .orElseThrow(() -> new NotFoundException("댓글을 찾을 수가 없습니다."));
        comment.update(command);
        return commentRepository.save(comment);
    }
}

package app.slicequeue.sq_board.comment.command.application;

import app.slicequeue.sq_board.comment.command.domain.Comment;
import app.slicequeue.sq_board.comment.command.domain.CommentId;
import app.slicequeue.sq_board.comment.command.domain.CommentPath;
import app.slicequeue.sq_board.comment.command.domain.CommentRepository;
import app.slicequeue.sq_board.comment.command.domain.dto.CreateCommentCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CreateCommentService {

    private final CommentRepository commentRepository;

    public CommentId createComment(CreateCommentCommand command) {
        Comment parent = findParent(command);
        CommentPath parentCommentPath = parent == null ? CommentPath.create("") : parent.getPath();

        Comment comment = Comment.create(command,
                parentCommentPath.createChildCommentPath(
                        commentRepository.findDescendantsTopPath(command.articleId(),
                                parentCommentPath.getPath()).orElse(null))
        );
        return commentRepository.save(comment).getCommentId();
    }

    private Comment findParent(CreateCommentCommand command) {
        CommentPath parentPath = command.parentPath();
        if (parentPath == null) {
            return null;
        }
        return commentRepository.findByPath(parentPath).orElseThrow();
    }

}

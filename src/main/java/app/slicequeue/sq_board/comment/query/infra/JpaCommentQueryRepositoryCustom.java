package app.slicequeue.sq_board.comment.query.infra;

import app.slicequeue.sq_board.comment.command.domain.CommentId;
import app.slicequeue.sq_board.comment.query.presentation.dto.CommentDetail;
import java.util.Optional;

public interface JpaCommentQueryRepositoryCustom {
    Optional<CommentDetail> findDetailBy(CommentId commentId);
}

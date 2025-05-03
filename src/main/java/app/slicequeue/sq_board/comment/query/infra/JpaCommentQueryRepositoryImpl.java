package app.slicequeue.sq_board.comment.query.infra;

import app.slicequeue.sq_board.comment.command.domain.Comment;
import app.slicequeue.sq_board.comment.command.domain.CommentId;
import app.slicequeue.sq_board.comment.query.presentation.dto.CommentDetail;
import jakarta.persistence.EntityManager;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JpaCommentQueryRepositoryImpl implements JpaCommentQueryRepositoryCustom {

  private final EntityManager em;

  @Override
  public Optional<CommentDetail> findDetailBy(CommentId commentId) {
    Comment comment = em.find(Comment.class, commentId);
    if (comment == null) {
      return Optional.empty();
    }
    return Optional.of(CommentDetail.from(comment));
  }
}

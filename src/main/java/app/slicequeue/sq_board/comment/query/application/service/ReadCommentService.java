package app.slicequeue.sq_board.comment.query.application.service;

import app.slicequeue.common.exception.NotFoundException;
import app.slicequeue.sq_board.comment.query.application.dto.ReadAllCommentsPageQuery;
import app.slicequeue.sq_board.comment.query.application.dto.ReadCommentDetailQuery;
import app.slicequeue.sq_board.comment.query.infra.JpaCommentQueryRepository;
import app.slicequeue.sq_board.comment.query.presentation.dto.CommentDetail;
import app.slicequeue.sq_board.comment.query.presentation.dto.PageResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReadCommentService {

  private final JpaCommentQueryRepository commentQueryRepository;

  public PageResponse<CommentDetail> readAll(ReadAllCommentsPageQuery query) {
    List<CommentDetail> comments = commentQueryRepository.findAllCommentDetailsBy(
        query.articleId().getId(), query.offset(), query.limit());
    long count = commentQueryRepository.count(query.articleId().getId(), query.limitForCount());
    return PageResponse.of(comments, count);
  }

  public CommentDetail read(ReadCommentDetailQuery query) {
    return commentQueryRepository.findById(query.commentId())
        .map(CommentDetail::from)
        .orElseThrow(() -> new NotFoundException("댓글을 찾을 수 없습니다."));
  }
}

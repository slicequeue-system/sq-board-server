package app.slicequeue.sq_board.comment.query.infra;

import static org.assertj.core.api.Assertions.assertThat;

import app.slicequeue.sq_board.article.command.domain.ArticleId;
import app.slicequeue.sq_board.comment.CommentTestFixture;
import app.slicequeue.sq_board.comment.command.domain.Comment;
import app.slicequeue.sq_board.comment.command.domain.CommentId;
import app.slicequeue.sq_board.comment.query.presentation.dto.CommentDetail;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Sql(scripts = "/db/init-comment.sql",
    executionPhase = ExecutionPhase.BEFORE_TEST_CLASS)
@DataJpaTest
class JpaCommentQueryRepositoryTest {

  @Autowired
  JpaCommentQueryRepository jpaCommentQueryRepository;

  @ParameterizedTest
  @MethodSource("pagingQueries")
  void 댓글을_복수_조회한다_엔티티_객체_리스트_반환(long articleId, long limit, long offset,
      int expectedSize) { // given

    // when
    List<Comment> results = jpaCommentQueryRepository.findAllBy(articleId, limit, offset);

    // then
    assertThat(results).hasSize(expectedSize);
    assertThat(results.stream().map(each -> each.getPath().getPath()))
        .isSortedAccordingTo(Comparator.comparing(String::valueOf));
  }

  @ParameterizedTest
  @MethodSource("pagingQueries")
  void 댓글을_복수_조회한다_댓글상세_객체_리스트_프로젝션_반환(
      long articleId, long limit, long offset, int expectedSize) { // given

    // when
    List<CommentDetail> results = jpaCommentQueryRepository.findAllCommentDetailsBy(articleId,
        limit, offset);

    // then
    assertThat(results).hasSize(expectedSize);
    assertThat(results.stream().map(CommentDetail::getCommentPath))
        .isSortedAccordingTo(Comparator.comparing(String::valueOf));
  }

  public Stream<Arguments> pagingQueries() {
    ArticleId articleId = CommentTestFixture.ARTICLE_ID1;
    return Stream.of(
        Arguments.of(articleId.getId(), 3, 0, 3),
        Arguments.of(articleId.getId(), 3, 3, 3),
        Arguments.of(articleId.getId(), 3, 6, 3),
        Arguments.of(articleId.getId(), 3, 9, 1)
    );
  }

  @Test
  void 댓글_개수를_집계한다() {
    // given
    ArticleId articleId = CommentTestFixture.ARTICLE_ID1;
    long limit = 3;
    int expectedCount = 3;

    // when
    long result = jpaCommentQueryRepository.count(articleId.getId(), limit);

    // then
    assertThat(result).isEqualTo(expectedCount);
  }

  @Test
  void 댓글_상세_조회한다() {
    // given
    CommentId commentId = CommentId.from(1L);

    // when
    Optional<CommentDetail> result = jpaCommentQueryRepository.findDetailBy(commentId);

    // then
    assertThat(result).isNotEmpty();
    assertThat(result.get().getCommentId()).isEqualTo(commentId.toString());
  }

  @Test
  void 댓글_상세_조회한다_삭제한_글은_조회결과가_없음() {
    // given
    CommentId commentId = CommentId.from(4L);

    // when
    Optional<CommentDetail> result = jpaCommentQueryRepository.findDetailBy(commentId);

    // then
    assertThat(result).isEmpty();
  }
}

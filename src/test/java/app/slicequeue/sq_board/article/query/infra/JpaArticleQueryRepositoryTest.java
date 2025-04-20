package app.slicequeue.sq_board.article.query.infra;

import app.slicequeue.sq_board.article.ArticleTestFixture;
import app.slicequeue.sq_board.article.command.domain.Article;
import app.slicequeue.sq_board.article.command.domain.ArticleId;
import app.slicequeue.sq_board.article.query.presentation.dto.ArticleDetail;
import app.slicequeue.sq_board.article.query.presentation.dto.ArticlePageResponse.ArticleListItem;
import app.slicequeue.sq_board.board.command.domain.BoardId;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DataJpaTest
class JpaArticleQueryRepositoryTest {

    @Autowired
    JpaArticleQueryRepository jpaArticleQueryRepository;

    BoardId boardId1 = ArticleTestFixture.BOARD_ID1;
    int exampleCount = 10;
    List<Article> all = new ArrayList<>();

    @BeforeAll
    void init() {
        List<Article> articleExamples = ArticleTestFixture.createExamples(exampleCount, boardId1,
                ArticleTestFixture.WRITER_ID1);
        for (Article articleExample : articleExamples) {
            all.add(jpaArticleQueryRepository.save(articleExample));
        }
    }

    @ParameterizedTest
    @MethodSource("pagingQueries")
    void 게시판의_게시글을_복수_조회한다_엔티티_객체_리스트_반환(long boardId, long offset, long limit, int expectedSize) { // given
        // when
        List<Article> results = jpaArticleQueryRepository.findAllBy(
                boardId,
                offset,
                limit);
        // then
        assertThat(results).hasSize(expectedSize);
    }

    @ParameterizedTest
    @MethodSource("pagingQueries")
    void 게시판의_게시글을_복수_조회한다_조회용_응답객체_리스트_프로젝션(long boardId, long offset, long limit, int expectedSize) { // given
        // when
        List<ArticleListItem> results = jpaArticleQueryRepository.findAllArticleListItemBy(
                boardId,
                offset,
                limit);
        // then
        assertThat(results).hasSize(expectedSize);
    }

    public Stream<Arguments> pagingQueries() {
        return Stream.of(
                Arguments.of(boardId1.getId(), 0, 3, 3),
                Arguments.of(boardId1.getId(), 3, 3, 3),
                Arguments.of(boardId1.getId(), 6, 3, 3),
                Arguments.of(boardId1.getId(), 9, 3, 1)
        );
    }

    @Test
    void 게시판의_게시글_개수를_집계한다() {
        // given
        BoardId boardId1 = ArticleTestFixture.BOARD_ID1;
        long limit = 3L;

        // when
        Long count = jpaArticleQueryRepository.count(boardId1.getId(), limit);

        // then
        assertThat(count).isEqualTo(limit);
    }

    @Test
    void 게시판의_게시글_개수를_집계시_없는_경우_0을반환() {
        // given
        BoardId boardId1 = BoardId.from(Long.MAX_VALUE);
        long limit = 3L;

        // when
        Long count = jpaArticleQueryRepository.count(boardId1.getId(), limit);

        // then
        assertThat(count).isEqualTo(0L);
    }

    @Test
    void 게시글_식별값으로_게시글_단건_상세조회를_한다() {
        // given
        Article first = all.getFirst();
        ArticleId targetId = first.getArticleId();
        // when
        Optional<ArticleDetail> resultOpt = jpaArticleQueryRepository.findArticleDetailBy(targetId);

        // then
        assertThat(resultOpt).isPresent();
        ArticleDetail result = resultOpt.get();
        assertThat(result.getArticleId()).isEqualTo(targetId.toString());
        assertThat(result.getTitle()).isEqualTo(first.getTitle());
        assertThat(result.getContent()).isEqualTo(first.getContent());
        assertThat(result.getWriterName()).isEqualTo(first.getWriterName());
        assertThat(result.getTags()).isEqualTo(first.getTags());
    }
}

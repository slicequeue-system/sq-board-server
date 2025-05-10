package app.slicequeue.sq_board.article_view.command.infra;

import app.slicequeue.sq_board.article.command.domain.ArticleId;
import app.slicequeue.sq_board.article_view.command.domain.ArticleViewCount;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class JpaArticleViewCountBackUpRepositoryTest {

    @Autowired
    JpaArticleViewCountBackUpRepository articleViewCountBackUpRepository;

    @Autowired
    TestEntityManager testEntityManager;

    @Test
    void updateViewCountTest() {
        // given
        ArticleId articleId = ArticleId.from(1L);
        ArticleViewCount saved = articleViewCountBackUpRepository.save(ArticleViewCount.init(articleId, 0L));
        testEntityManager.flush();
        testEntityManager.clear();

        // when - 100, 300, 200 순으로 동시에 많은 요청이 왔을때 대비
        int result1 = articleViewCountBackUpRepository.updateViewCount(articleId, 100L);
        int result2 = articleViewCountBackUpRepository.updateViewCount(articleId, 300L);
        int result3 = articleViewCountBackUpRepository.updateViewCount(articleId, 200L);

        // then
        assertThat(result1).isOne();
        assertThat(result2).isOne();
        assertThat(result3).isZero();

        Optional<ArticleViewCount> countOptional = articleViewCountBackUpRepository.findById(articleId);
        assertThat(countOptional).isPresent();
        ArticleViewCount articleViewCount = countOptional.get();
        assertThat(articleViewCount.getViewCount()).isEqualTo(300L);
    }
}

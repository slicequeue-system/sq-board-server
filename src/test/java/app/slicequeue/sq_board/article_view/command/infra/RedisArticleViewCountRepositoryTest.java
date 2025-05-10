package app.slicequeue.sq_board.article_view.command.infra;

import app.slicequeue.sq_board.article.command.domain.ArticleId;
import app.slicequeue.sq_board.article_view.command.domain.ArticleViewCount;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
        RedisTestConfig.class, RedisContainerHolder.class,
        RedisArticleViewCountRepository.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RedisArticleViewCountRepositoryTest {

    @Autowired
    private RedisArticleViewCountRepository repository;

    @Test
    void increaseAndReadTest() {
        ArticleId id = ArticleId.from(123L);
        repository.insertIfAbsent(ArticleViewCount.init(id, 100));
        assertThat(repository.read(id)).isEqualTo(100L);

        repository.increase(id);
        assertThat(repository.read(id)).isEqualTo(101L);
    }
}

package app.slicequeue.sq_board.article_view.command.infra;

import app.slicequeue.sq_board.article.command.domain.ArticleId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
        RedisTestConfig.class,
        RedisContainerHolder.class,
        RedisArticleViewDistributedLockRepository.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RedisArticleViewDistributedLockRepositoryTest {

    @Autowired
    private RedisArticleViewDistributedLockRepository repository;

    @Test
    void lockTest() throws InterruptedException {
        // given
        ArticleId articleId = ArticleId.from(123L);
        long userId = 1L;
        Duration ttl = Duration.ofSeconds(3);

        // when
        boolean lock1 = repository.lock(articleId, userId, ttl);
        boolean lock2 = repository.lock(articleId, userId, ttl);
        Thread.sleep(ttl.toMillis() + 100);
        boolean lock3 = repository.lock(articleId, userId, ttl);

        assertThat(lock1).isTrue();
        assertThat(lock2).isFalse();
        assertThat(lock3).isTrue();
    }
}

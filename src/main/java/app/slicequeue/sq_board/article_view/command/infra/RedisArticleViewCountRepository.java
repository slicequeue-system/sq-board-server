package app.slicequeue.sq_board.article_view.command.infra;

import app.slicequeue.sq_board.article.command.domain.ArticleId;
import app.slicequeue.sq_board.article_view.command.domain.ArticleViewCountRepository;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class RedisArticleViewCountRepository implements ArticleViewCountRepository {

    private final StringRedisTemplate redisTemplate;

    // view::article::{article_id}::view_count
    String KEY_FORMAT = "view::article::%s::view_count";
    private String generateKey(long articleId) {
        return KEY_FORMAT.formatted(articleId);
    }

    @Override
    public long read(@NotNull ArticleId articleId) {
        String result = redisTemplate.opsForValue().get(generateKey(articleId.getId()));
        return result == null ? 0L : Long.parseLong(result);
    }

    @Override
    public long increase(@NotNull ArticleId articleId) {
        return redisTemplate.opsForValue().increment(generateKey(articleId.getId()));
    }
}

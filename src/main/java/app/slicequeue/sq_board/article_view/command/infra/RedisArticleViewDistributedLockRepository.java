package app.slicequeue.sq_board.article_view.command.infra;


import app.slicequeue.sq_board.article.command.domain.ArticleId;
import app.slicequeue.sq_board.article_view.command.domain.ArticleViewDistributedLockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;

@Repository
@RequiredArgsConstructor
public class RedisArticleViewDistributedLockRepository implements ArticleViewDistributedLockRepository {
    private final StringRedisTemplate redisTemplate;

    // view:article::{article_id}::user::{user_id}::lock
    private static final String KEY_FORMAT = "view:article::{article_id}::user::{user_id}::lock";

    private String generateKey(ArticleId articleId, long userId) {
        return KEY_FORMAT.formatted(articleId, userId);
    }

    public boolean lock(ArticleId articleId, long userId, Duration ttl) {
        String key = generateKey(articleId, userId);
        return redisTemplate.opsForValue().setIfAbsent(key, "", ttl);
    }

}

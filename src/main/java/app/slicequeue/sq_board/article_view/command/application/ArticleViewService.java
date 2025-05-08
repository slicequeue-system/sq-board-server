package app.slicequeue.sq_board.article_view.command.application;

import app.slicequeue.sq_board.article.command.domain.ArticleId;
import app.slicequeue.sq_board.article_view.command.domain.ArticleViewCountRepository;
import app.slicequeue.sq_board.article_view.command.domain.ArticleViewDistributedLockRepository;
import app.slicequeue.sq_board.article_view.command.domain.dto.IncreaseArticleViewCountCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class ArticleViewService {
    private final ArticleViewCountRepository articleViewCountRepository;
    private final ArticleViewCountBackUpProcessor articleViewCountBackUpProcessor;
    private final ArticleViewDistributedLockRepository articleViewDistributedLockRepository;

    private static final int BACK_UP_BATCH_SIZE = 100;
    private static final Duration TTL = Duration.ofMinutes(10);

    public long increase(IncreaseArticleViewCountCommand command) {
        if (!articleViewDistributedLockRepository.lock(command.articleId(), command.userId(), TTL)) {
            return articleViewCountRepository.read(command.articleId());
        }

        long count = articleViewCountRepository.increase(command.articleId());
        if (count % BACK_UP_BATCH_SIZE == 0) {
            articleViewCountBackUpProcessor.backUp(command.articleId(), count);
        }
        return count;
    }

    public long count(ArticleId articleId) {
        return articleViewCountRepository.read(articleId);
    }
}

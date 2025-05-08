package app.slicequeue.sq_board.article_view.command.application.service;

import app.slicequeue.sq_board.article.command.domain.ArticleId;
import app.slicequeue.sq_board.article_view.command.domain.ArticleViewCount;
import app.slicequeue.sq_board.article_view.command.domain.ArticleViewCountBackUpRepository;
import app.slicequeue.sq_board.article_view.command.domain.ArticleViewCountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
@RequiredArgsConstructor
public class ArticleViewCountBackUpProcessor {
    private final ArticleViewCountBackUpRepository articleViewCountBackUpRepository;
    private final ArticleViewCountRepository articleViewCountRepository;

    @Transactional
    public void backUp(ArticleId articleId, Long viewCount) {
        int result = articleViewCountBackUpRepository.updateViewCount(articleId, viewCount);
        if (result == 0) {
            articleViewCountBackUpRepository.findById(articleId).ifPresentOrElse(ignored -> { },
                    () -> articleViewCountBackUpRepository.save(ArticleViewCount.init(articleId, viewCount)));
        }
    }

    public void restoreViewCounts() {
        List<ArticleViewCount> allCounts = articleViewCountBackUpRepository.findAll();
        try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
            allCounts.forEach(count ->
                    executor.submit(() -> articleViewCountRepository.insertIfAbsent(count.getArticleId(), count))
            );
        } // try-with-resources 로 Executor 자동 종료
    }
}

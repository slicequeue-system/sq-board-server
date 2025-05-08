package app.slicequeue.sq_board.article_view.command.application;

import app.slicequeue.sq_board.article.command.domain.ArticleId;
import app.slicequeue.sq_board.article_view.command.domain.ArticleViewCountRepository;
import app.slicequeue.sq_board.article_view.command.domain.dto.IncreaseArticleViewCountCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ArticleViewService {
    private final ArticleViewCountRepository articleViewCountRepository;
    private final ArticleViewCountBackUpProcessor articleViewCountBackUpProcessor;
    private static final int BACK_UP_BATCH_SIZE = 100;

    public long increase(IncreaseArticleViewCountCommand command) {
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

package app.slicequeue.sq_board.article_view.command.application;

import app.slicequeue.sq_board.article.command.domain.ArticleId;
import app.slicequeue.sq_board.article_view.command.domain.ArticleViewCountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ArticleViewService {

    private final ArticleViewCountRepository articleViewCountRepository;

    public long increase(ArticleId articleId, Long userId) {
        return articleViewCountRepository.increase(articleId);
    }

    public long count(ArticleId articleId) {
        return articleViewCountRepository.read(articleId);
    }
}

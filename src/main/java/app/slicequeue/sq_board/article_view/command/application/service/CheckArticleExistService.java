package app.slicequeue.sq_board.article_view.command.application.service;

import app.slicequeue.sq_board.article.command.domain.ArticleId;
import app.slicequeue.sq_board.article.command.domain.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CheckArticleExistService {

    private final ArticleRepository articleRepository;

    public boolean checkExist(ArticleId articleId) {
        return articleRepository.findByArticleIdAndDeletedAtIsNull(articleId).isPresent();
    }
}

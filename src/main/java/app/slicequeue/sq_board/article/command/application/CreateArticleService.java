package app.slicequeue.sq_board.article.command.application;

import app.slicequeue.sq_board.article.command.domain.Article;
import app.slicequeue.sq_board.article.command.domain.ArticleId;
import app.slicequeue.sq_board.article.command.domain.ArticleRepository;
import app.slicequeue.sq_board.article.command.domain.dto.CreateArticleCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CreateArticleService {

    private final ArticleRepository articleRepository;

    public Article createArticle(CreateArticleCommand command) {
        Article article = Article.create(command);
        return articleRepository.save(article);
    }
}

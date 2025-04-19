package app.slicequeue.sq_board.article.command.application;

import app.slicequeue.common.exception.NotFoundException;
import app.slicequeue.sq_board.article.command.domain.Article;
import app.slicequeue.sq_board.article.command.domain.ArticleId;
import app.slicequeue.sq_board.article.command.domain.ArticleRepository;
import app.slicequeue.sq_board.article.command.domain.dto.DeleteArticleCommand;
import app.slicequeue.sq_board.article.command.domain.dto.UpdateArticleCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class DeleteArticleService {

    private final ArticleRepository articleRepository;

    public ArticleId deleteArticle(DeleteArticleCommand command) {
        Article article = articleRepository.findByArticleIdAndDeletedAtIsNull(command.articleId())
                .orElseThrow(() -> new NotFoundException("게시글을 찾을 수 없습니다."));
        article.delete();
        articleRepository.save(article);
        return article.getArticleId();
    }
}

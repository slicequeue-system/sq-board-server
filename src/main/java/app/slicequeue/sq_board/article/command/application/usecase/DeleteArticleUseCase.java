package app.slicequeue.sq_board.article.command.application.usecase;

import app.slicequeue.common.exception.NotFoundException;
import app.slicequeue.sq_board.article.command.application.service.DeleteArticleService;
import app.slicequeue.sq_board.article.command.application.service.SummarizeBoardArticleCountService;
import app.slicequeue.sq_board.article.command.domain.Article;
import app.slicequeue.sq_board.article.command.domain.ArticleId;
import app.slicequeue.sq_board.article.command.domain.dto.DeleteArticleCommand;
import app.slicequeue.sq_board.common.util.ConstraintViolationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
public class DeleteArticleUseCase {

    private final DeleteArticleService deleteArticleService;
    private final SummarizeBoardArticleCountService summarizeBoardArticleCountService;

    public ArticleId execute(DeleteArticleCommand command) {
        try {
            return executeWithTransaction(command);
        } catch (DataIntegrityViolationException e) {
            if (ConstraintViolationUtils.isForeignKeyViolation(e)) {
                throw new NotFoundException("존재하지 않는 게시판입니다.");
            }
            throw e;
        }
    }

    @Transactional
    public ArticleId executeWithTransaction(DeleteArticleCommand command) {
        AtomicReference<Article> article = new AtomicReference<>();
        summarizeBoardArticleCountService.decreaseWithLock(
                () -> {
                    Article deleted = deleteArticleService.deleteArticle(command);
                    article.set(deleted);
                    return deleted;
                });
        return article.get().getArticleId();
    }
}

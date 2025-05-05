package app.slicequeue.sq_board.article.command.application;

import app.slicequeue.common.exception.NotFoundException;
import app.slicequeue.sq_board.article.command.domain.ArticleId;
import app.slicequeue.sq_board.article.command.domain.dto.BoardArticleCountCommand;
import app.slicequeue.sq_board.article.command.domain.dto.CreateArticleCommand;
import app.slicequeue.sq_board.common.util.ConstraintViolationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
public class CreateArticleUseCase {

    private final CreateArticleService createArticleService;
    private final SummarizeBoardArticleCountService summarizeBoardArticleCountService;

    public ArticleId execute(CreateArticleCommand command) {
        try {
            return executeWithTransaction(command);
        } catch (DataIntegrityViolationException e) {
            if (ConstraintViolationUtils.isForeignKeyViolation(e)) {
                throw new NotFoundException("존재하지 않는 게시판 입니다.");
            }
            throw e;
        }
    }

    @Transactional
    public ArticleId executeWithTransaction(CreateArticleCommand command) {
        AtomicReference<ArticleId> articleId = new AtomicReference<>();
        summarizeBoardArticleCountService.increaseWithLock(
                BoardArticleCountCommand.from(command), () -> articleId.set(createArticleService.createArticle(command)));
        return articleId.get();
    }
}

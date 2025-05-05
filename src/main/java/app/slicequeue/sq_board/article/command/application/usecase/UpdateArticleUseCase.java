package app.slicequeue.sq_board.article.command.application.usecase;

import app.slicequeue.common.exception.NotFoundException;
import app.slicequeue.sq_board.article.command.application.service.UpdateArticleService;
import app.slicequeue.sq_board.article.command.domain.ArticleId;
import app.slicequeue.sq_board.article.command.domain.dto.UpdateArticleCommand;
import app.slicequeue.sq_board.common.util.ConstraintViolationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UpdateArticleUseCase {

    private final UpdateArticleService updateArticleService;

    public ArticleId execute(UpdateArticleCommand command) {
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
    private ArticleId executeWithTransaction(UpdateArticleCommand command) {
        return updateArticleService.updateArticle(command).getArticleId();
    }

}

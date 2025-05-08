package app.slicequeue.sq_board.article_view.command.application.usecase;

import app.slicequeue.common.exception.NotFoundException;
import app.slicequeue.sq_board.article_view.command.application.service.ArticleViewService;
import app.slicequeue.sq_board.article_view.command.application.service.CheckArticleExistService;
import app.slicequeue.sq_board.article_view.command.domain.dto.IncreaseArticleViewCountCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IncreaseArticleViewCountUseCase {

    private final CheckArticleExistService checkArticleExistService;
    private final ArticleViewService articleViewService;

    public long execute(IncreaseArticleViewCountCommand command) {
        if (!checkArticleExistService.checkExist(command.articleId())) {
            throw new NotFoundException("존재하지 않는 게시글 입니다.");
        }
        return articleViewService.increase(command);
    }
}

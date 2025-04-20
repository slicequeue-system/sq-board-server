package app.slicequeue.sq_board.article.query.application.service;

import app.slicequeue.common.exception.NotFoundException;
import app.slicequeue.sq_board.article.query.application.dto.ReadArticleDetailQuery;
import app.slicequeue.sq_board.article.query.infra.JpaArticleQueryRepository;
import app.slicequeue.sq_board.article.query.presentation.dto.ArticleDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReadDetailArticleService {

    private final JpaArticleQueryRepository articleQueryRepository;

    public ArticleDetail get(ReadArticleDetailQuery query) {
        return articleQueryRepository.findArticleDetailBy(query.articleId()).orElseThrow(() -> new NotFoundException(
                "게시글을 찾을 수 없습니다."));
    }

}

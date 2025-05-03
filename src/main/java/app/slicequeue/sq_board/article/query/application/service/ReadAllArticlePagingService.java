package app.slicequeue.sq_board.article.query.application.service;

import app.slicequeue.sq_board.article.query.application.dto.ReadAllArticlesPagingQuery;
import app.slicequeue.sq_board.article.query.infra.JpaArticleQueryRepository;
import app.slicequeue.sq_board.article.query.presentation.dto.ArticleListItem;
import app.slicequeue.sq_board.article.query.presentation.dto.ArticlePageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReadAllArticlePagingService {

    private final JpaArticleQueryRepository articlePagingQueryRepository;
    private static final int movablePageCount = 10;

    public ArticlePageResponse findAll(ReadAllArticlesPagingQuery query) {
        List<ArticleListItem> articles = articlePagingQueryRepository.findAllBy(
            query.boardId().getId(),
            query.offset(),
            query.size()
        ).stream().map(ArticleListItem::from).toList();
        long count = articlePagingQueryRepository.count(query.boardId().getId(), query.limitForCount(movablePageCount));
        return ArticlePageResponse.of(articles, count);
    }

}

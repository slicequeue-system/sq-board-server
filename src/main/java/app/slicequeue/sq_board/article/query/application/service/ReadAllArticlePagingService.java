package app.slicequeue.sq_board.article.query.application.service;

import app.slicequeue.sq_board.article.query.application.dto.ReadAllArticlePagingQuery;
import app.slicequeue.sq_board.article.query.infra.JpaArticlePagingQueryRepository;
import app.slicequeue.sq_board.article.query.presentation.dto.ArticlePageResponse;
import app.slicequeue.sq_board.article.query.presentation.dto.ArticlePageResponse.ArticleListItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReadAllArticlePagingService {

    private final JpaArticlePagingQueryRepository articlePagingQueryRepository;
    private static final int movablePageCount = 10;

    public ArticlePageResponse readAll(ReadAllArticlePagingQuery query) {
        List<ArticleListItem> articles = articlePagingQueryRepository.findAllBy(query.boardId().getId(),
                query.offset(), query.size()).stream().map(ArticleListItem::from).toList();
        long count = articlePagingQueryRepository.count(query.boardId().getId(), query.limitForCount(movablePageCount));
        return ArticlePageResponse.of(articles, count);
    }

}

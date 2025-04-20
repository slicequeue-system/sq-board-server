package app.slicequeue.sq_board.article.query.application.service;

import app.slicequeue.sq_board.article.query.application.dto.ReadAllArticleInfiniteScrollQuery;
import app.slicequeue.sq_board.article.query.infra.JpaArticleQueryRepository;
import app.slicequeue.sq_board.article.query.presentation.dto.ArticleListItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReadAllArticleInfiniteScrollService {

    private final JpaArticleQueryRepository articlePagingQueryRepository;

    public List<ArticleListItem> findAll(ReadAllArticleInfiniteScrollQuery query) {
        if (query.lastArticleId() == null) {
            return articlePagingQueryRepository.findAllArticleListItemInfiniteScroll(query.boardId(),
                    query.pageSize());
        }
        return articlePagingQueryRepository.findAllArticleListItemInfiniteScroll(
                query.boardId(), query.pageSize(), query.lastArticleId());
    }
}

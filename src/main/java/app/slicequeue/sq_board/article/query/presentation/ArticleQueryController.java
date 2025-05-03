package app.slicequeue.sq_board.article.query.presentation;

import app.slicequeue.common.dto.CommonResponse;
import app.slicequeue.sq_board.article.query.application.dto.ReadAllArticleInfiniteScrollQuery;
import app.slicequeue.sq_board.article.query.application.dto.ReadAllArticlesPagingQuery;
import app.slicequeue.sq_board.article.query.application.dto.ReadArticleDetailQuery;
import app.slicequeue.sq_board.article.query.application.service.ReadAllArticleInfiniteScrollService;
import app.slicequeue.sq_board.article.query.application.service.ReadAllArticlePagingService;
import app.slicequeue.sq_board.article.query.application.service.ReadDetailArticleService;
import app.slicequeue.sq_board.article.query.presentation.dto.ArticleDetail;
import app.slicequeue.sq_board.article.query.presentation.dto.ArticleListItem;
import app.slicequeue.sq_board.article.query.presentation.dto.ArticlePageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/articles")
@RequiredArgsConstructor
public class ArticleQueryController {

    private final ReadAllArticlePagingService readAllArticlePagingService;
    private final ReadAllArticleInfiniteScrollService readAllArticleInfiniteScrollService;
    private final ReadDetailArticleService readDetailArticleService;

    @GetMapping
    public CommonResponse<ArticlePageResponse> readAll(
            @RequestParam("boardId") Long boardId,
            @RequestParam(value = "page", defaultValue = "0") Long page,
            @RequestParam(value = "size", defaultValue = "10") Long size) {
        ReadAllArticlesPagingQuery query = ReadAllArticlesPagingQuery.of(boardId, page, size);
        ArticlePageResponse response = readAllArticlePagingService.findAll(query);
        return CommonResponse.success(response);
    }

    @GetMapping("/infinite-scroll")
    public CommonResponse<List<ArticleListItem>> readAllInfiniteScroll(
            @RequestParam("boardId") Long boardId,
            @RequestParam(value = "size", required = false, defaultValue = "15") Long pageSize,
            @RequestParam(value = "lastArticleId", required = false) Long lastArticleId) {
        ReadAllArticleInfiniteScrollQuery query = ReadAllArticleInfiniteScrollQuery.of(boardId, pageSize, lastArticleId);
        List<ArticleListItem> all = readAllArticleInfiniteScrollService.findAll(query);
        return CommonResponse.success(all);
    }

    @GetMapping("/{articleId}")
    public CommonResponse<ArticleDetail> read(@PathVariable("articleId") Long articleId) {
        ReadArticleDetailQuery query = ReadArticleDetailQuery.from(articleId);
        ArticleDetail detail = readDetailArticleService.get(query);
        return CommonResponse.success(detail);
    }

}

package app.slicequeue.sq_board.article.query.presentation;

import app.slicequeue.common.dto.CommonResponse;
import app.slicequeue.sq_board.article.query.application.dto.ReadAllArticlePagingQuery;
import app.slicequeue.sq_board.article.query.application.dto.ReadArticleDetailQuery;
import app.slicequeue.sq_board.article.query.application.service.ReadAllArticlePagingService;
import app.slicequeue.sq_board.article.query.application.service.ReadDetailArticleService;
import app.slicequeue.sq_board.article.query.presentation.dto.ArticleDetail;
import app.slicequeue.sq_board.article.query.presentation.dto.ArticlePageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/articles")
@RequiredArgsConstructor
public class ArticleQueryController {

    private final ReadAllArticlePagingService readAllArticlePagingService;
    private final ReadDetailArticleService readDetailArticleService;

    @GetMapping
    public CommonResponse<ArticlePageResponse> readAll(
            @RequestParam("boardId") Long boardId,
            @RequestParam(value = "page", defaultValue = "0") Long page,
            @RequestParam(value = "size", defaultValue = "10") Long size) {
        ReadAllArticlePagingQuery query = ReadAllArticlePagingQuery.of(boardId, page, size);
        ArticlePageResponse response = readAllArticlePagingService.readAll(query);
        return CommonResponse.success(response);
    }

    @GetMapping("/{articleId}")
    public CommonResponse<ArticleDetail> read(@PathVariable("articleId") Long articleId) {
        ReadArticleDetailQuery query = ReadArticleDetailQuery.from(articleId);
        ArticleDetail detail = readDetailArticleService.get(query);
        return CommonResponse.success(detail);
    }

}

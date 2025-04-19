package app.slicequeue.sq_board.article.query.presentation;

import app.slicequeue.common.dto.CommonResponse;
import app.slicequeue.sq_board.article.query.application.dto.ReadAllArticlePagingQuery;
import app.slicequeue.sq_board.article.query.application.service.ReadAllArticlePagingService;
import app.slicequeue.sq_board.article.query.presentation.dto.ArticlePageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/articles")
@RequiredArgsConstructor
public class ArticleQueryController {

    private final ReadAllArticlePagingService readAllArticlePagingService;

    @GetMapping
    public CommonResponse<ArticlePageResponse> readAll(
            @RequestParam("boardId") Long boardId,
            @RequestParam(value = "page", defaultValue = "0") Long page,
            @RequestParam(value = "size", defaultValue = "10") Long size) {
        ReadAllArticlePagingQuery query = ReadAllArticlePagingQuery.of(boardId, page, size);
        ArticlePageResponse response = readAllArticlePagingService.readAll(query);
        return CommonResponse.success(response);
    }

}

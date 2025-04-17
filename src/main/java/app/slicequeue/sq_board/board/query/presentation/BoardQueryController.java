package app.slicequeue.sq_board.board.query.presentation;

import app.slicequeue.common.dto.CommonResponse;
import app.slicequeue.sq_board.board.query.application.dto.ReadAllByInfiniteScrollQuery;
import app.slicequeue.sq_board.board.query.application.dto.ReadAllByPageQuery;
import app.slicequeue.sq_board.board.query.application.service.ReadAllBoardService;
import app.slicequeue.sq_board.board.query.presentation.dto.BoardListItem;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/boards")
@RequiredArgsConstructor
public class BoardQueryController {

    private final ReadAllBoardService readAllBoardService;

    @GetMapping
    public CommonResponse<Page<BoardListItem>> readAll(
            @RequestParam("projectId") Long projectId, @PageableDefault(sort = "createdAt,desc") Pageable pageable) {
        ReadAllByPageQuery query = ReadAllByPageQuery.of(projectId, pageable);
        Page<BoardListItem> all = readAllBoardService.readAll(query);
        return CommonResponse.success(all);
    }

    @GetMapping("/infinite-scroll")
    public CommonResponse<List<BoardListItem>> readAllInfiniteScroll(@RequestParam("projectId") Long projectId,
                                                                     @RequestParam(value = "size", defaultValue =
                                                                             "10") Long pageSize,
                                                                     @RequestParam(value = "lastBoardId", required = false) Long lastBoardId) {
        ReadAllByInfiniteScrollQuery query = ReadAllByInfiniteScrollQuery.of(projectId, pageSize, lastBoardId);
        List<BoardListItem> all = readAllBoardService.readAllInfiniteScroll(query);
        return CommonResponse.success(all);
    }
}

package app.slicequeue.sq_board.board.query.application.service;

import app.slicequeue.sq_board.board.query.application.dto.ReadAllByInfiniteScrollQuery;
import app.slicequeue.sq_board.board.query.application.dto.ReadAllByPageQuery;
import app.slicequeue.sq_board.board.query.infra.JpaBoardPagingQueryRepository;
import app.slicequeue.sq_board.board.query.presentation.dto.BoardListItem;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReadAllBoardService {

    private final JpaBoardPagingQueryRepository boardPagingQueryRepository;

    public Page<BoardListItem> readAll(ReadAllByPageQuery query) {
        validateReadAllParam(query);
        return boardPagingQueryRepository.findAllBoardListItemsByProjectId(query.projectId(), query.pageable());
    }

    private void validateReadAllParam(ReadAllByPageQuery query) {
        Assert.notNull(query, "query must not be null");
        Assert.notNull(query.projectId(), "projectId must not be null");
        Assert.notNull(query.pageable(), "pageable must not be null");
    }

    public List<BoardListItem> readAllInfiniteScroll(ReadAllByInfiniteScrollQuery query) {
        validateReadAllInfiniteScrollParam(query);
        if (query.lastBoardId() == null) {
            return boardPagingQueryRepository.findAllBoardListItemsInfiniteScroll(query.projectId(), query.pageSize());
        }
        return boardPagingQueryRepository.findAllBoardListItemsInfiniteScroll(
                query.projectId(),
                query.pageSize(),
                query.lastBoardId());
    }

    private void validateReadAllInfiniteScrollParam(ReadAllByInfiniteScrollQuery query) {
        Assert.notNull(query, "query must not be null");
        Assert.notNull(query.projectId(), "projectId must not be null");
        Assert.notNull(query.pageSize(), "projectId must not be null");
    }
}

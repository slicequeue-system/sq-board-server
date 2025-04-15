package app.slicequeue.sq_board.board.query.application.service;

import app.slicequeue.sq_board.board.query.application.dto.ReadAllByPageQuery;
import app.slicequeue.sq_board.board.query.infra.JpaBoardPagingQueryRepository;
import app.slicequeue.sq_board.board.query.presentation.dto.BoardListItem;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
@RequiredArgsConstructor
public class ReadAllBoardService {

    JpaBoardPagingQueryRepository boardPagingQueryRepository;

    public Page<BoardListItem> readAll(ReadAllByPageQuery query) {
        validateReadAllByPageQuery(query);
        return boardPagingQueryRepository.findAllBoardListItemsByProjectId(query.projectId(), query.pageable());
    }

    private void validateReadAllByPageQuery(ReadAllByPageQuery query) {
        Assert.notNull(query, "query must not be null");
        Assert.notNull(query.projectId(), "projectId must not be null");
        Assert.notNull(query.pageable(), "pageable must not be null");
    }
}

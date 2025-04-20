package app.slicequeue.sq_board.board.query.application.service;

import app.slicequeue.common.exception.NotFoundException;
import app.slicequeue.sq_board.board.query.application.dto.ReadDetailQuery;
import app.slicequeue.sq_board.board.query.infra.JpaBoardQueryRepository;
import app.slicequeue.sq_board.board.query.presentation.dto.BoardDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReadDetailBoardService {

    private final JpaBoardQueryRepository boardPagingQueryRepository;

    public BoardDetail readDetail(ReadDetailQuery query) {
        validateReadDetailParam(query);
        return boardPagingQueryRepository.findBoardDetailBy(query.projectId(), query.boardId().getId())
                .orElseThrow(() -> new NotFoundException(String.format("board not found. %s", query)));
    }

    private static void validateReadDetailParam(ReadDetailQuery query) {
        Assert.notNull(query, "query must not be null.");
        Assert.notNull(query.projectId(), "projectId must not be null.");
        Assert.notNull(query.boardId(), "boardId must not be null.");
    }

}

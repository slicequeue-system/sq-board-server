package app.slicequeue.sq_board.board.query.application.dto;

import app.slicequeue.sq_board.board.command.domain.BoardId;

public record ReadDetailQuery(Long projectId, BoardId boardId) {

    public static ReadDetailQuery of(Long projectId, Long boardId) {
        return new ReadDetailQuery(projectId, BoardId.from(boardId));
    }
}

package app.slicequeue.sq_board.board.command.domain.dto;

import app.slicequeue.sq_board.board.command.domain.BoardId;
import app.slicequeue.sq_board.board.command.presentation.dto.UpdateBoardRequest;

public record UpdateBoardCommand(BoardId boardId, String name, Long adminId, String description) {

    public static UpdateBoardCommand of(Long boardId, UpdateBoardRequest request) {
        return new UpdateBoardCommand(
                BoardId.from(boardId),
                request.getName(),
                request.getAdminId(),
                request.getDescription());
    }
}

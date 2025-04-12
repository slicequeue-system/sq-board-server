package app.slicequeue.sq_board.board.command.domain.dto;

import app.slicequeue.sq_board.board.command.presentation.dto.CreateBoardRequest;

public record CreateBoardCommand(String name, Long projectId, Long adminId, String description) {

    public static CreateBoardCommand from(CreateBoardRequest request) {
        return new CreateBoardCommand(request.getName(), request.getProjectId(), request.getAdminId(), request.getDescription());
    }
}

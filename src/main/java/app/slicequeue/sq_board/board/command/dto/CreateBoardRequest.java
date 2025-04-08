package app.slicequeue.sq_board.board.command.dto;

public record CreateBoardRequest(String name, Long projectId, Long adminId, String description) {

}

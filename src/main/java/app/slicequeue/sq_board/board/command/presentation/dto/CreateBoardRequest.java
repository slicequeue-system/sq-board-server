package app.slicequeue.sq_board.board.command.presentation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CreateBoardRequest {

    @NotNull(message = "게시판 이름 값이 null 일 수 없습니다.")
    @NotBlank(message = "게시판 이름 값이 빈 또는 공백 문자일 수 없습니다.")
    String name;
    @NotNull(message = "게시판 프로젝트 식별 값이 null 일 수 없습니다.")
    Long projectId;
    @NotNull(message = "게시판 관리자 식별 값이 null 일 수 없습니다.")
    Long adminId;
    String description;

    public CreateBoardRequest(String name, Long projectId, Long adminId, String description) {
        this.name = name;
        this.projectId = projectId;
        this.adminId = adminId;
        this.description = description;
    }
}

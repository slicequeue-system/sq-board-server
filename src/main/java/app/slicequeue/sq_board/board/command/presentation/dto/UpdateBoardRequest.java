package app.slicequeue.sq_board.board.command.presentation.dto;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class UpdateBoardRequest {

    @NotNull(message = "게시판 이름 값이 null 일 수 없습니다.")
    @NotBlank(message = "게시판 이름 값이 빈 또는 공백 문자일 수 없습니다.")
    private final String name;
    // 이 필드는 오히려 포함되면 안됨, client 측에서 기대하는 수정이 없을 것이라는 것을 명시 해야만함
    private final Long projectId;

    @NotNull(message = "게시판 관리자 식별 값이 null 일 수 없습니다.")
    private final Long adminId;
    private final String description;

    public UpdateBoardRequest(String name, Long projectId, Long adminId, String description) {
        this.name = name;
        this.projectId = projectId;
        this.adminId = adminId;
        this.description = description;
    }

    @AssertTrue(message = "projectId는 null 이어야 합니다.")
    public boolean isProjectIdNull() {
        return projectId == null;
    }
}

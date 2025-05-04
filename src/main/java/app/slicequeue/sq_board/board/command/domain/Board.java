package app.slicequeue.sq_board.board.command.domain;

import app.slicequeue.common.base.time_entity.BaseTimeSoftDeletedAtEntity;
import app.slicequeue.sq_board.board.command.domain.dto.CreateBoardCommand;
import app.slicequeue.sq_board.board.command.domain.dto.UpdateBoardCommand;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.util.Assert;

@Table(name = "board", indexes = {@Index(name = "idx_project_id_board_id", columnList = "project_id,board_id desc")})
@Getter
@Entity
@ToString
@NoArgsConstructor
public class Board extends BaseTimeSoftDeletedAtEntity {

    static final int MIN_SIZE_NAME = 1;
    static final int MAX_SIZE_NAME = 100;
    static final int MAX_SIZE_DESCRIPTION = 512;

    @EmbeddedId
    @AttributeOverride(name = "id", column = @Column(name = "board_id"))
    private BoardId boardId;
    @NotNull
    @NotBlank
    @Size(min = MIN_SIZE_NAME, max = MAX_SIZE_NAME)
    private String name;
    @NotNull
    private Long projectId;
    @NotNull
    private Long adminId;
    @Size(max = MAX_SIZE_DESCRIPTION)
    private String description;

    public static Board create(String name, Long projectId, Long adminId, String description) {
        Board board = new Board();
        board.boardId = BoardId.generateId();
        board.name = validateName(name);
        board.projectId = validateProjectId(projectId);
        board.adminId = validateAdminId(adminId);
        board.description = validateDescription(description);
        return board;
    }

    private static String validateName(String name) {
        Assert.notNull(name, "name must not be null");
        Assert.hasText(name, "name must not be blank");
        if (name.length() > MAX_SIZE_NAME)
            throw new IllegalArgumentException(String.format(
                    "name must be length %s ~ %s", MIN_SIZE_NAME, MAX_SIZE_NAME));
        return name;
    }

    private static long validateProjectId(Long projectId) {
        Assert.notNull(projectId, "projectId must not be null");
        return projectId;
    }


    private static long validateAdminId(Long adminId) {
        Assert.notNull(adminId, "adminId must not be null");
        return adminId;
    }

    private static String validateDescription(String description) {
        if (description != null && description.length() > MAX_SIZE_DESCRIPTION)
            throw new IllegalArgumentException(String.format(
                    "description must be max length %s", MAX_SIZE_DESCRIPTION));
        return description;
    }

    public static Board create(CreateBoardCommand request) {
        return create(request.name(), request.projectId(), request.adminId(), request.description());
    }

    public void update(UpdateBoardCommand command) {
        this.name = validateName(command.name());
        this.adminId = validateAdminId(command.adminId());
        this.description = command.description();
        // 프로젝트 식별값은 수정할 수 없다
    }
}

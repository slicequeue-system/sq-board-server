package app.slicequeue.sq_board.board.command.domain;

import app.slicequeue.common.base.time_entity.BaseTimeSoftDeleteEntity;
import app.slicequeue.sq_board.board.command.domain.dto.CreateBoardCommand;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.util.Assert;

@Table(name = "board", indexes = {@Index(name = "idx_project_id_board_id", columnList = "project_id,board_id desc")})
@Getter
@Entity
@ToString
@NoArgsConstructor
public class Board extends BaseTimeSoftDeleteEntity {

    @EmbeddedId
    private BoardId boardId;
    @NotNull
    private String name;
    @NotNull
    private Long projectId;
    @NotNull
    private Long adminId;
    private String description;

    public static Board create(String name, Long projectId, Long adminId, String description) {
        Board board = new Board();
        board.boardId = BoardId.generateId();
        board.name = validateName(name);
        board.projectId = validateProjectId(projectId);
        board.adminId = validateAdminId(adminId);
        board.description = description;
        return board;
    }

    private static String validateName(String name) {
        Assert.notNull(name, "name must not be null");
        if (name.isBlank()) {
            throw new IllegalArgumentException("name must not be blank");
        }
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

    public static Board create(CreateBoardCommand request) {
        return create(request.name(), request.projectId(), request.adminId(), request.description());
    }
}

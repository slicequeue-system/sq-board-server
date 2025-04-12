package app.slicequeue.sq_board.board.command.domain;

import app.slicequeue.common.base.time_entity.BaseTimeSoftDeleteEntity;
import app.slicequeue.sq_board.board.command.domain.dto.CreateBoardRequest;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

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

    public static Board create(String name, long projectId, long adminId, String description) {
        Board board = new Board();
        board.boardId = BoardId.generateId();
        board.name = name;
        board.projectId = projectId;
        board.adminId = adminId;
        board.description = description;
        return board;
    }

    public static Board create(CreateBoardRequest request) {
        return create(request.name(), request.projectId(), request.adminId(), request.description());
    }
}

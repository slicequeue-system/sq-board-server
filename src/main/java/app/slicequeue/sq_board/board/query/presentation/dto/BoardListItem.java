package app.slicequeue.sq_board.board.query.presentation.dto;

import lombok.Getter;

import java.time.Instant;

@Getter
public class BoardListItem {

    private final String boardId;
    private final String name;
    private final String projectId;
    private final String adminId;
    private final Instant createdAt;
    private final Instant updatedAt;
    // TODO 관리자이름


    public BoardListItem(
            String boardId,
            String name,
            String projectId,
            String adminId,
            Instant createdAt,
            Instant updatedAt) {
        this.boardId = boardId;
        this.name = name;
        this.projectId = projectId;
        this.adminId = adminId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public BoardListItem(
            Long boardId,
            String name,
            Long projectId,
            Long adminId,
            Instant createdAt,
            Instant updatedAt) {
        this(String.valueOf(boardId),
                name,
                String.valueOf(projectId),
                String.valueOf(adminId),
                createdAt,
                updatedAt);
    }
}

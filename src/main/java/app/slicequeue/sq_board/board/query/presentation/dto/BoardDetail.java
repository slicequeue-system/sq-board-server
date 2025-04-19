package app.slicequeue.sq_board.board.query.presentation.dto;

import lombok.Getter;

import java.time.Instant;

@Getter
public class BoardDetail {

    private final String boardId;
    private final String name;
    private final String description;
    private final String projectId;
    private final String adminId;
    private final Instant createdAt;
    private final Instant updatedAt;

    public BoardDetail(
            String boardId,
            String name,
            String description,
            String projectId,
            String adminId,
            Instant createdAt,
            Instant updatedAt) {
        this.boardId = boardId;
        this.name = name;
        this.description = description;
        this.projectId = projectId;
        this.adminId = adminId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public BoardDetail(
            Long boardId,
            String name,
            String description,
            Long projectId,
            Long adminId,
            Instant createdAt,
            Instant updatedAt) {
        this(String.valueOf(boardId),
                name,
                description,
                String.valueOf(projectId),
                String.valueOf(adminId),
                createdAt,
                updatedAt);
    }
}

package app.slicequeue.sq_board.board.query.application.dto;

public record ReadAllByInfiniteScrollQuery(Long projectId, Long pageSize, Long lastBoardId) {

    public static ReadAllByInfiniteScrollQuery of(Long projectId, Long pageSize, Long lastBoardId) {
        return new ReadAllByInfiniteScrollQuery(projectId, pageSize, lastBoardId);
    }
}

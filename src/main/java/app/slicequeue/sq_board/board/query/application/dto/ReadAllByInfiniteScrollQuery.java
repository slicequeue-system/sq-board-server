package app.slicequeue.sq_board.board.query.application.dto;

public record ReadAllByInfiniteScrollQuery(Long projectId, Long pageSize, Long lastBoardId) {

    public static ReadAllByInfiniteScrollQuery of(Long projectId, Long pageSize, String lastBoardId) {
        Long parsedLastBoardId = null;
        if (lastBoardId != null) {
            try {
                parsedLastBoardId = Long.valueOf(lastBoardId);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("lastBoardId는 Long 타입이어야 합니다.");
            }
        }
        return new ReadAllByInfiniteScrollQuery(projectId, pageSize, parsedLastBoardId);
    }
}

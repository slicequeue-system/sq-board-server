package app.slicequeue.sq_board.board.query.application.dto;

import org.springframework.data.domain.Pageable;

public record ReadAllByPageQuery(Long projectId, Pageable pageable) {

    public static ReadAllByPageQuery of(Long projectId, Pageable pageable) {
        return new ReadAllByPageQuery(projectId, pageable);
    }
}

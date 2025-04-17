package app.slicequeue.sq_board.board.query.application.dto;

import org.springframework.data.domain.Pageable;

public record ReadAllByPageQuery(Long projectId, Pageable pageable) {
}

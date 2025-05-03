package app.slicequeue.sq_board.comment.query.presentation.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class PageResponse<T> {

    private List<T> contents;
    private long count;

    public static <T> PageResponse<T> of(List<T> contents, long count) {
        PageResponse<T> response = new PageResponse<>();
        response.contents = contents;
        response.count = count;
        return response;
    }
}

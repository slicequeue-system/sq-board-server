package app.slicequeue.sq_board.article.command.domain.dto;

import app.slicequeue.sq_board.article.command.presentation.dto.CreateArticleRequest;
import app.slicequeue.sq_board.board.command.domain.BoardId;
import lombok.Builder;

import java.util.List;

@Builder
public record CreateArticleCommand(
        BoardId boardId,
        String title,
        String content,
        List<String> tags,
        Long writerId,
        String writerName) {

    public static CreateArticleCommand from(CreateArticleRequest request) {

        return new CreateArticleCommand(
                BoardId.from(Long.valueOf(request.getBoardId())),
                request.getTitle(),
                request.getContent(),
                request.getTags(),
                Long.valueOf(request.getWriterId()),
                request.getWriterName());
    }
}

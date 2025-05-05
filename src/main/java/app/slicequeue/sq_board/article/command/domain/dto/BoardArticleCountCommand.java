package app.slicequeue.sq_board.article.command.domain.dto;

import app.slicequeue.sq_board.board.command.domain.BoardId;

import java.time.Instant;

public record BoardArticleCountCommand(BoardId boardId, Instant lastCreatedAt) {
    public static BoardArticleCountCommand from(CreateArticleCommand command) {
        return new BoardArticleCountCommand(command.boardId(), Instant.now());
    }
}

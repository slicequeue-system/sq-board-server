package app.slicequeue.sq_board.comment.command.presentation.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class CreateCommentRequest {
    @Size(min = 1, max = 3000)
    @NotNull(message = "content must not be null")
    String content;
    @NotNull(message = "articleId must not be null")
    Long articleId;
    @NotNull(message = "writerId must not be null")
    Long writerId;
    @NotNull(message = "writerNickname must not be null")
    String writerNickname;
    String parentPath;
}

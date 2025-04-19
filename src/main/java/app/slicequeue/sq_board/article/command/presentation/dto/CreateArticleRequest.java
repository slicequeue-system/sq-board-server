package app.slicequeue.sq_board.article.command.presentation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.List;

@Getter
public class CreateArticleRequest {

    @NotNull(message = "boardId must not be null")
    private final String boardId;
    @NotBlank(message = "title must not be blank")
    private final String title;
    @NotBlank(message = "content must not be blank")
    private final String content;
    private final List<String> tags;
    @NotNull(message = "writerId must not be null")
    private final String writerId;
    @NotBlank(message = "writerName must not be blank")
    private final String writerName;

    public CreateArticleRequest(
            String boardId,
            String title,
            String content,
            List<String> tags,
            String writerId,
            String writerName) {
        this.boardId = boardId;
        this.title = title;
        this.content = content;
        this.tags = tags;
        this.writerId = writerId;
        this.writerName = writerName;
    }
}

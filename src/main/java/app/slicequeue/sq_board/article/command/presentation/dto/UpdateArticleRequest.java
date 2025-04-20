package app.slicequeue.sq_board.article.command.presentation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.List;

@Getter
public class UpdateArticleRequest {

    @NotBlank(message = "title must not be blank")
    private final String title;
    @NotBlank(message = "content must not be blank")
    private final String content;
    private final List<String> tags;

    public UpdateArticleRequest(
            String title,
            String content,
            List<String> tags) {
        this.title = title;
        this.content = content;
        this.tags = tags;
    }
}

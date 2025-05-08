package app.slicequeue.sq_board.article_view.command.presentation.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class IncreaseArticleViewCountRequest {

    @NotNull(message = "게시글 식별값이 없습니다.")
    private Long articleId;

    @NotNull(message = "사용자 식별값이 없습니다.")
    private Long userId;
}

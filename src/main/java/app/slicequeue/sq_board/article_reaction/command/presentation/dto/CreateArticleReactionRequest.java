package app.slicequeue.sq_board.article_reaction.command.presentation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateArticleReactionRequest {

    @NotNull(message = "게시글 식별값이 누락되었습니다.")
    private Long articleId;
    @NotNull(message = "사용자 식별값이 누락되었습니다.")
    private Long userId;
    @NotBlank(message = "이모지 값이 누락되었습니다.")
    private String emoji;
}

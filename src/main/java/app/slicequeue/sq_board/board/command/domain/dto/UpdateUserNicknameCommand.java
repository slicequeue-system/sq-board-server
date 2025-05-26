package app.slicequeue.sq_board.board.command.domain.dto;

import app.slicequeue.sq_board.common.validation.SelfValidating;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class UpdateUserNicknameCommand extends SelfValidating<UpdateUserNicknameCommand> {

    @NotNull(message = "userId")
    private long userId;

    @NotBlank(message = "nickname")
    private String nickname;

    public UpdateUserNicknameCommand(long userId, String nickname) {
        this.userId = userId;
        this.nickname = nickname;
        validateSelf();
    }
}

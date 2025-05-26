package app.slicequeue.sq_board.common.dto;

import app.slicequeue.sq_board.common.event.payload.UserInfoChangedEventPayload;
import app.slicequeue.sq_board.common.validation.SelfValidating;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class UpdateUserNicknameCommand extends SelfValidating<UpdateUserNicknameCommand> {

    @NotNull(message = "userId")
    private final long userId;

    @NotBlank(message = "nickname")
    private final String nickname;

    private UpdateUserNicknameCommand(long userId, String nickname) {
        this.userId = userId;
        this.nickname = nickname;
        validateSelf();
    }

    public static UpdateUserNicknameCommand from(UserInfoChangedEventPayload payload) {
        return new UpdateUserNicknameCommand(payload.getUserId(), payload.getNickname());
    }
}

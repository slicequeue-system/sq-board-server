package app.slicequeue.sq_board.common.event.payload;

import app.slicequeue.event.domain.EventPayload;
import lombok.*;

import java.time.Instant;
import java.util.Map;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoChangedEventPayload implements EventPayload {

    private long userId;
    private long projectId;
    private String loginId;
    private UserState state;
    private String nickname;
    private Map<String, Object> profile;
    private Instant createdAt;
    private Instant updatedAt;

    public enum UserState {

        ACTIVE,
        WAIT,
        WITHDRAW
        ;
    }
}

package app.slicequeue.sq_board.common.event.handler;

import app.slicequeue.event.domain.Event;
import app.slicequeue.event.domain.EventHandler;
import app.slicequeue.sq_board.application.dto.UpdateUserNicknameCommand;
import app.slicequeue.sq_board.common.event.payload.UserInfoChangedEventPayload;
import app.slicequeue.sq_board.common.event.type.EventType;
import app.slicequeue.sq_board.application.facade.UpdateUserNicknameFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserInfoChangedEventHandler implements EventHandler<UserInfoChangedEventPayload> {

    private final UpdateUserNicknameFacade updateUserNicknameFacade;

    @Override
    public void handle(Event event) {
        UserInfoChangedEventPayload payload = event.extractPayload(UserInfoChangedEventPayload.class);
        updateUserNicknameFacade.update(UpdateUserNicknameCommand.from(payload));
    }

    @Override
    public boolean supports(Event event) {
        return EventType.USER_INFO_CHANGED.check(event);
    }
}

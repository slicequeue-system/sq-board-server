package app.slicequeue.sq_board.common.event.handler;

import app.slicequeue.event.domain.Event;
import app.slicequeue.event.domain.EventHandler;
import app.slicequeue.sq_board.common.event.payload.UserInfoChangedEventPayload;
import app.slicequeue.sq_board.common.event.type.EventType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserInfoChangedEventHandler implements EventHandler<UserInfoChangedEventPayload> {



    @Override
    public void handle(Event event) {

        // FIXME 구현 - 아래는 로그 확인 임시 코드
        System.out.println("!!!!!!!");
        System.out.println(event);
        System.out.println("!!!!!!!");
        UserInfoChangedEventPayload payload = event.extractPayload(UserInfoChangedEventPayload.class);
        System.out.println(payload);
    }

    @Override
    public boolean supports(Event event) {
        return EventType.USER_INFO_CHANGED.check(event);
    }
}

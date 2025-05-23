package app.slicequeue.sq_board.common.event.consumer;

import app.slicequeue.event.domain.Event;
import app.slicequeue.event.domain.EventPayload;
import app.slicequeue.sq_board.common.event.payload.UserInfoChangedEventPayload;
import app.slicequeue.sq_board.common.event.type.EventType;
import app.slicequeue.sq_board.common.util.DataSerializer;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserEventConsumer {

    private final UserEventHandleProvider userEventHandleProvider;

    @KafkaListener(topics = {
            EventType.Topic.SQ_USER_INFO_CHANGED,
    })
    public void listen(String message, Acknowledgment ack) {
        log.info("[UserEventConsumer.listen] message={}", message);

        Event event = Event.fromJson(message, EventType.class);
        if (event != null) {
            userEventHandleProvider.handleEvent(event);
        }
        ack.acknowledge();
    }

    @Getter
    public static class EventRaw {
        private Long eventId;
        private String type;
        private Object payload;
    }
}

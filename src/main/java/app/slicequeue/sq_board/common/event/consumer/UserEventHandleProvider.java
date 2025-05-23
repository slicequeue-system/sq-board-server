package app.slicequeue.sq_board.common.event.consumer;

import app.slicequeue.event.domain.Event;
import app.slicequeue.event.domain.EventHandler;
import app.slicequeue.event.domain.EventPayload;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.List;

@Log4j2
@Component
@RequiredArgsConstructor
public class UserEventHandleProvider {

    private final List<EventHandler> eventHandlers;

    public void handleEvent(Event event) {
        for (EventHandler eventHandler : eventHandlers) {
            if (eventHandler.supports(event)) {
                eventHandler.handle(event);
            }
        }
    }
}

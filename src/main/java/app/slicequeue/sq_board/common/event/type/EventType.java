package app.slicequeue.sq_board.common.event.type;

import app.slicequeue.event.domain.Event;
import app.slicequeue.event.domain.EventDescriptor;

public enum EventType implements EventDescriptor {

    USER_INFO_CHANGED(Topic.SQ_USER_INFO_CHANGED)
    ;

    private final String topic;

    EventType(String topic) {
        this.topic = topic;
    }

    @Override
    public String getCode() {
        return name();
    }

    @Override
    public String getTopic() {
        return topic;
    }

    public boolean check(Event event) {
        return getCode().equals(event.getType().getCode());
    }

    public static class Topic {
        public static final String SQ_USER_INFO_CHANGED = "sq-user-info-changed";
    }
}

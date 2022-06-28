package triple.mileage.service.factory;

import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import triple.mileage.domain.Event;
import triple.mileage.domain.Review;
import triple.mileage.dto.EventDTO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Component
public class EventFactory {

    private final Map<Event, Function<EventDTO.REVIEW, Review>> eventServices = new HashMap<>();

    public EventFactory(List<EventService> eventServices) {
        if (CollectionUtils.isEmpty(eventServices)) {
            throw new IllegalArgumentException("존재하지 않는 method 입니다");
        }
        for (EventService service : eventServices) {
            Function<EventDTO.REVIEW, Review> transaction = service::transaction;
            this.eventServices.put(service.getEventType(), transaction);
        }
    }

    public Function<EventDTO.REVIEW, Review> getEvent(Event event) {
        return eventServices.get(event);
    }

}

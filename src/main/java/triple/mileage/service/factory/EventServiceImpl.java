package triple.mileage.service.factory;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import triple.mileage.domain.Event;
import triple.mileage.dto.EventDTO;

@Service
@RequiredArgsConstructor
public class EventServiceImpl {

    private final EventFactory eventFactory;

    public void getEvent(EventDTO.REVIEW reviewDto) {
        Event event = Event.valueOf(reviewDto.getAction());
        eventFactory.getEvent(event).apply(reviewDto);
    }
}

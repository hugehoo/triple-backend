package triple.mileage.service.factory;

import triple.mileage.domain.Event;
import triple.mileage.domain.Review;
import triple.mileage.dto.EventDTO;

public interface EventService {

    Event getEventType();
    Review transaction(EventDTO.REVIEW reviewDto);

}

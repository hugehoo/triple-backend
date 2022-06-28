package triple.mileage.service.factory;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import triple.mileage.domain.Event;
import triple.mileage.domain.Review;
import triple.mileage.dto.EventDTO;
import triple.mileage.dto.ReviewDto;

@Service
@RequiredArgsConstructor
public class EventServiceImpl {

    private final EventFactory eventFactory;

    public ReviewDto getEvent(EventDTO.REVIEW reviewDto) {
        Event event = Event.valueOf(reviewDto.getAction());
        Review review = eventFactory.getEvent(event).apply(reviewDto);
        return ReviewDto.of(review);
    }
}

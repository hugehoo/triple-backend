package triple.mileage.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import triple.mileage.domain.Review;
import triple.mileage.dto.EventDTO;
import triple.mileage.dto.ReviewDto;
import triple.mileage.service.factory.EventServiceImpl;
import triple.mileage.service.UserService;

import javax.validation.Valid;

@RestController
@RequestMapping("/event")
@RequiredArgsConstructor
public class EventController {

    private final UserService userService;
    private final EventServiceImpl eventServiceImpl;


    @GetMapping("/{userId}")
    public Object getUserPoint(@PathVariable String userId) {
        return userService.getUserPoint(userId);
    }

    @PostMapping
    public ReviewDto transactReview(
            @Valid @RequestBody EventDTO.REVIEW reviewDto
    ) {
        return eventServiceImpl.getEvent(reviewDto);
    }

}

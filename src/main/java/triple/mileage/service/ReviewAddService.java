package triple.mileage.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import triple.mileage.commons.error.exceptions.DuplicateReviewException;
import triple.mileage.domain.*;
import triple.mileage.dto.EventDTO;
import triple.mileage.repository.*;
import triple.mileage.service.factory.EventService;

import java.util.*;

@Component
@RequiredArgsConstructor
public class ReviewAddService implements EventService {

    private final PlaceRepository placeRepository;
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final PhotoRepository photoRepository;
    private final PointLogRepository pointLogRepository;

    @Override
    public Event getEventType() {
        return Event.ADD;
    }

    @Override
    @Transactional
    public Review transaction(EventDTO.REVIEW reviewDto) {

        Place place = placeRepository.findPlaceById(reviewDto.getPlaceId());
        User user = userRepository.findUserById(reviewDto.getUserId());

        reviewRepository.findReviewByUserAndPlaceAndStatus(user, place, "NORMAL").ifPresent(r -> {
            throw new DuplicateReviewException("이미 작성한 리뷰가 존재합니다.");
        });

        List<Review> reviews = reviewRepository.findReviewByPlaceAndStatus(place);
        Integer firstReviewPoint = checkFirstReview(reviews);

        Map<String, Integer> points = processPoints(user, reviewDto, place);
        Review review = createReview(reviewDto, place, user, firstReviewPoint);

        createPhotos(reviewDto, review);
        createPointLog(points, review);
        return review;
    }

    private void createPointLog(Map<String, Integer> points, Review review) {
        Pointlog pointLog = PointLogUtils.pointLogBuilder(review, points);
        pointLogRepository.save(pointLog);
    }

    private void createPhotos(EventDTO.REVIEW reviewDto, Review savedReview) {
        List<Photo> photos = ReviewUtils.createImages(reviewDto.getAttachedPhotoIds(), savedReview);
        photoRepository.saveAll(photos);
    }

    private Map<String, Integer> processPoints(User user, EventDTO.REVIEW reviewDto, Place place) {
        List<Review> reviews = reviewRepository.findReviewByPlaceAndStatus(place);
        int additionalPoints = transactPoints(reviewDto) + checkFirstReview(reviews);
        int finalPoints = user.getPoint() + additionalPoints;
        user.setPoint(finalPoints);
        Map<String, Integer> map = new HashMap<>();
        map.put("additional", additionalPoints);
        map.put("final", finalPoints);
        return map;
    }

    private Review createReview(EventDTO.REVIEW reviewDto, Place place, User user, Integer firstReviewPoint) {
        return reviewRepository.save(reviewDto.toEntity(place, user, firstReviewPoint));
    }

    private void createPointLog(Review review, Map<String, Integer> points) {
        Place place1 = review.getPlace();
        User user1 = review.getUser();
        Pointlog pointLog = Pointlog.builder()
                .userId(user1.getId().toString())
                .placeId(place1.getId().toString())
                .reviewId(review.getId().toString())
                .status("ADD")
                .updatePoint(points.get("additional"))
                .lastPoint(points.get("final"))
                .build();

        pointLogRepository.save(pointLog);
    }

    public int transactPoints(EventDTO.REVIEW reviewDto) {
        return checkContent(reviewDto)
                + ReviewUtils.checkImages(reviewDto);
    }

    public Integer checkFirstReview(List<Review> reviews) {
        if (reviews.size() == 0) {
            return 1;
        }
        return 0;
    }

    public Integer checkContent(EventDTO.REVIEW reviewDto) {
        String content = reviewDto.getContent();
        if (content.length() >= 1) {
            return 1;
        }
        return 0;
    }
}

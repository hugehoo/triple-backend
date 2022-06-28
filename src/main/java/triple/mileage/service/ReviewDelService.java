package triple.mileage.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import triple.mileage.commons.error.exceptions.NoContentException;
import triple.mileage.domain.*;
import triple.mileage.dto.EventDTO;
import triple.mileage.repository.*;
import triple.mileage.service.factory.EventService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class ReviewDelService implements EventService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final PhotoRepository photoRepository;
    private final PointLogRepository pointLogRepository;


    @Override
    public Event getEventType() {
        return Event.DELETE;
    }

    @Override
    @Transactional
    public Review transaction(EventDTO.REVIEW reviewDto) {

        Review review = reviewRepository.findReviewById(reviewDto.getReviewId());

        if (review.getStatus().equals("NONE")) {
            throw new NoContentException("이미 삭제된 리뷰 입니다.");
        }
        review.setStatus("NONE");

        List<Photo> photos = deleteTransactPhotos(review);
        Map<String, Integer> points = processPoints(reviewDto, review, photos);
        createPointLog(review, points);
        return null;
    }

    private void createPointLog(Review review, Map<String, Integer> points) {
        Pointlog pointLog = PointLogUtils.pointLogBuilder(review, points);
        pointLogRepository.save(pointLog);
    }

    private Map<String, Integer> processPoints(EventDTO.REVIEW reviewDto, Review review, List<Photo> photos) {
        int updatePoints = 1 + checkFirstReview(review) + checkImage(photos);
        User user = userRepository.findUserById(reviewDto.getUserId());
        int finalPoints = user.getPoint() - updatePoints;
        user.setPoint(finalPoints);

        Map<String, Integer> points = new HashMap<>();
        points.put("additional", updatePoints);
        points.put("final", finalPoints);
        return points;
    }

    private List<Photo> deleteTransactPhotos(Review review) {
        List<Photo> photos = photoRepository.findPhotoByReviewAndStatus(review, "NORMAL");
        photos.forEach(photo -> photo.setStatus("NONE"));
        return photos;
    }

    private Integer checkImage(List<Photo> photoIds) {
        if (photoIds.size() > 0) {
            return 1;
        }
        return 0;
    }

    public Integer checkFirstReview(Review review) {
        if (review.isFirstReview()) {
            return 1;
        }
        return 0;
    }
}

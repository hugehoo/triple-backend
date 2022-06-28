package triple.mileage.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import triple.mileage.commons.error.exceptions.NoContentException;
import triple.mileage.domain.*;
import triple.mileage.dto.EventDTO;
import triple.mileage.repository.*;
import triple.mileage.service.factory.EventService;

import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ReviewModService implements EventService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final PhotoRepository photoRepository;
    private final PointLogRepository pointLogRepository;

    @Override
    public Event getEventType() {
        return Event.MOD;
    }


    @Override
    public Review transaction(EventDTO.REVIEW reviewDto) {

        Review review = reviewRepository.findReviewById(reviewDto.getReviewId());

        if (review.getStatus().equals("NONE")) {
            throw new NoContentException("이미 삭제된 리뷰 입니다.");
        }

        review.setContent(reviewDto.getContent());

        User user = userRepository.findUserById(reviewDto.getUserId());
        List<Photo> originalPhotos = photoRepository.findPhotoByReviewAndStatus(review, "NORMAL");
        List<UUID> originalPhotoUUID = getUuids(reviewDto, originalPhotos);
        List<UUID> newPhotos = extractNewPhotos(reviewDto, originalPhotoUUID);
        Map<String, Integer> points = processPoints(reviewDto, user, originalPhotos);

        createPhotos(review, newPhotos);
        createPointLog(review, points);

        return review;
    }

    private void createPointLog(Review review, Map<String, Integer> points) {
        Pointlog pointLog = PointLogUtils.pointLogBuilder(review, points);
        pointLogRepository.save(pointLog);
    }

    private  List<UUID> extractNewPhotos(EventDTO.REVIEW reviewDto, List<UUID> originalPhotoUUID) {
        List<UUID> newPhotos = new ArrayList<>();
        reviewDto.getAttachedPhotoIds().forEach(newPhoto -> {
            if (!originalPhotoUUID.contains(newPhoto)) {
                newPhotos.add(newPhoto);
            }
        });
        return newPhotos;
    }

    private void createPhotos(Review review, List<UUID> newPhotos) {
        List<Photo> photos = ReviewUtils.createImages(newPhotos, review);
        photoRepository.saveAll(photos);
    }

    private Map<String, Integer>  processPoints(EventDTO.REVIEW reviewDto, User user, List<Photo> originalPhotos) {
        int imagePoint = ReviewUtils.checkImages(reviewDto);
        int additionalPoints = getAdditionalPoints(originalPhotos, imagePoint);
        int finalPoints = user.getPoint() + additionalPoints;
        user.setPoint(finalPoints);

        Map<String, Integer> map = new HashMap<>();
        map.put("additional", additionalPoints);
        map.put("final", finalPoints);
        return map;
    }

    private int getAdditionalPoints(List<Photo> originalPhotos, int imagePoint) {
        int originalPhotoSize = originalPhotos.size();
        int additionalPoints = transactPoints(originalPhotoSize, imagePoint);
        return additionalPoints;
    }

    private List<UUID> getUuids(EventDTO.REVIEW reviewDto, List<Photo> originalPhotos) {
        List<UUID> originalPhotoUUID = originalPhotos.stream()
                .peek(original -> transactPhotoStatus(reviewDto, original))
                .map(Photo::getId)
                .collect(Collectors.toList());
        return originalPhotoUUID;
    }

    private void transactPhotoStatus(EventDTO.REVIEW reviewDto, Photo original) {
        if (!reviewDto.getAttachedPhotoIds().contains(original.getId())) {
            original.setStatus("NONE");
        }
    }

    private int transactPoints(int originalSize, int imagePoint) {
        if (originalSize > 0 && imagePoint == 0) {
            return -1;
        } else if (originalSize == 0 && imagePoint > 0) {
            return 1;
        }
        return 0;
    }

}

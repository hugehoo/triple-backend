package triple.mileage.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import triple.mileage.domain.Pointlog;
import triple.mileage.domain.Review;
import triple.mileage.dto.EventDTO;
import triple.mileage.repository.PhotoRepository;
import triple.mileage.repository.PointLogRepository;
import triple.mileage.repository.ReviewRepository;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
public class ReviewDeleteMockTest {

    @Autowired
    ReviewAddService reviewAddService;

    @Autowired
    ReviewDelService reviewDelService;

    @Autowired
    PointLogRepository pointLogRepository;

    EventDTO.REVIEW reviewDto;
    EventDTO.REVIEW reviewAfterDelete;
    Review review;

    @BeforeEach
    public void setupDto() {

        List<UUID> imageList = Collections.singletonList(UUID.fromString("11ecf28c-462c-159d-bdc8-03fd26019cff"));

        UUID userAId = UUID.fromString("78e74b34-8987-4962-8a31-51efcd15511d");
        UUID userBId = UUID.fromString("46026146-9cc9-44f0-b62c-e3e833fdc7d4");
        UUID placeId = UUID.fromString("a992488a-a4f5-4d85-afa0-7a93c7a65ecc");
        UUID reviewId = UUID.fromString("d54f1057-945c-43d0-af7a-85fb565a0539");

        reviewDto = EventDTO.REVIEW.builder()
                .type("REVIEW")
                .action("ADD")
                .content("가오슝")
                .reviewId(reviewId)
                .userId(userAId)
                .placeId(placeId)
                .attachedPhotoIds(imageList)
                .build();

        reviewAfterDelete = EventDTO.REVIEW.builder()
                .type("REVIEW")
                .action("ADD")
                .content("가오슝")
                .reviewId(reviewId)
                .userId(userBId)
                .placeId(placeId)
                .attachedPhotoIds(imageList)
                .build();

        createSampleReview();
    }

    private void createSampleReview() {
        review = reviewAddService.transaction(reviewDto);
        reviewDto.setReviewId(review.getId());
    }

    @Test
    @DisplayName("리뷰 삭제 후 보너스 포인트를 회수한다.")
    public void Retrieve_Bonus_Point_After_Delete_Review() {
        reviewDelService.transaction(reviewDto);

        List<Pointlog> pointLogs = pointLogRepository.findAll();
        List<Pointlog> lastTwoLogs = pointLogs.stream().skip(pointLogs.size() - 2).collect(Collectors.toList());

        final int PHOTO_BONUS_POINT = 1;
        final int CONTENT_POINT = 1;
        final int FIRST_REVIEW_BONUS_POINT = 1;

        final long ORIGINAL_POINT = lastTwoLogs.get(0).getLastPoint();
        final long RETRIEVED_POINT = lastTwoLogs.get(1).getLastPoint();

        assertEquals(ORIGINAL_POINT, RETRIEVED_POINT + PHOTO_BONUS_POINT + CONTENT_POINT + FIRST_REVIEW_BONUS_POINT);
    }

    @Test
    @DisplayName("첫 리뷰 보너스 포인트 리뷰가 삭제 된 후, 다른 사용자가 해당 장소를 리뷰하면 첫 리뷰 보너스 포인트를 부여한다.")
    public void First_Review_Bonus_Point_After_Retrieve() {

        // Delete - 사용자 입장 첫 리뷰 삭제
        reviewDelService.transaction(reviewDto);

        // Add - 다른 유저에 의해 해당 장소의 첫 리뷰 다시 생성
        reviewAddService.transaction(reviewAfterDelete);

        List<Pointlog> pointLogs = pointLogRepository.findAll();
        List<Pointlog> lastTwoLogs = pointLogs.stream().skip(pointLogs.size() - 2).collect(Collectors.toList());

        final long LOST_POINT_BY_DELETE = Math.abs(lastTwoLogs.get(0).getUpdatePoint());
        final long ADDED_POINT_BY_FIRST_REVIEW = lastTwoLogs.get(1).getUpdatePoint();

        assertEquals(LOST_POINT_BY_DELETE, ADDED_POINT_BY_FIRST_REVIEW);
    }
}

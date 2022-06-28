package triple.mileage.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import triple.mileage.domain.*;
import triple.mileage.dto.EventDTO;
import triple.mileage.repository.*;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest
@Transactional
public class ReviewModMockTest {

    @Autowired
    ReviewAddService reviewAddService;

    @Autowired
    ReviewModService reviewModService;

    @Autowired
    PlaceRepository placeRepository;

    @Autowired
    PointLogRepository pointLogRepository;

    EventDTO.REVIEW reviewDto;
    EventDTO.REVIEW updateReviewDto;

    @BeforeEach
    public void setupDto() {

        List<UUID> imageList = Collections.singletonList(UUID.fromString("11ecf28c-462c-159d-bdc8-03fd26019cff"));

        reviewDto = EventDTO.REVIEW.builder()
                .type("REVIEW")
                .action("ADD")
                .content("가오슝")
                .reviewId(UUID.fromString("d54f1057-945c-43d0-af7a-85fb565a0540"))
                .userId(UUID.fromString("78e74b34-8987-4962-8a31-51efcd15511d"))
                .placeId(UUID.fromString("a992488a-a4f5-4d85-afa0-7a93c7a65ecc"))
                .attachedPhotoIds(imageList)
                .build();


        updateReviewDto = EventDTO.REVIEW.builder()
                .type("REVIEW")
                .action("MOD")
                .content("수정 시 사진을 없앤다.")
                .userId(UUID.fromString("78e74b34-8987-4962-8a31-51efcd15511d"))
                .placeId(UUID.fromString("a992488a-a4f5-4d85-afa0-7a93c7a65ecc"))
                .attachedPhotoIds(Collections.emptyList())
                .build();

        createSampleReview();
    }

    private void createSampleReview() {
        Review review = reviewAddService.transaction(reviewDto);
        updateReviewDto.setReviewId(review.getId());
    }

    @Test
    @DisplayName("최초 리뷰 작성시 사진을 첨부했다가, 수정시 사진을 지우면 사진 포인트를 회수한다.")
    public void Modify_Photos_To_Empty_List() {

        reviewModService.transaction(updateReviewDto);

        List<Pointlog> pointLogs = pointLogRepository.findAll();
        List<Pointlog> lastTwoLogs = pointLogs.stream().skip(pointLogs.size() - 2).collect(Collectors.toList());

        final int PHOTO_BONUS_POINT = 1;
        final long POINT_WITH_PHOTO = lastTwoLogs.get(0).getLastPoint();
        final long POINT_WITHOUT_PHOTO = lastTwoLogs.get(1).getLastPoint();

        assertEquals(POINT_WITH_PHOTO, POINT_WITHOUT_PHOTO + PHOTO_BONUS_POINT);
    }
}

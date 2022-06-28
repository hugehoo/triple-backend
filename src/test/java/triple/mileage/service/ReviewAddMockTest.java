package triple.mileage.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import triple.mileage.domain.Place;
import triple.mileage.domain.Pointlog;
import triple.mileage.domain.Review;
import triple.mileage.dto.EventDTO;
import triple.mileage.repository.PlaceRepository;
import triple.mileage.repository.PointLogRepository;
import triple.mileage.repository.ReviewRepository;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
public class ReviewAddMockTest {

    @Autowired
    ReviewAddService reviewAddService;

    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    PlaceRepository placeRepository;

    @Autowired
    PointLogRepository pointLogRepository;

    EventDTO.REVIEW build;
    EventDTO.REVIEW placeFirstReview;

    UUID firstPlaceUUID;

    List<UUID> imageList;

    @BeforeEach
    public void setupDto() {

        imageList = Arrays.asList(
                UUID.fromString("11ecf28c-462c-159d-bdc8-03fd26019cff"),
                UUID.fromString("f9943a8f-fe37-4d7d-9511-e754b0f9f335"));

        build = EventDTO.REVIEW.builder()
                .type("REVIEW")
                .action("ADD")
                .content("타이완")
                .userId(UUID.fromString("46026146-9cc9-44f0-b62c-e3e833fdc7d4"))
                .placeId(UUID.fromString("3fec5466-630f-4313-bbea-0fff58819836"))
                .attachedPhotoIds(imageList)
                .build();


        firstPlaceUUID = UUID.fromString("a992488a-a4f5-4d85-afa0-7a93c7a65ecc");

        placeFirstReview = EventDTO.REVIEW.builder()
                .type("REVIEW")
                .action("ADD")
                .content("성 패트릭 성당")
                .reviewId(UUID.fromString("d54f1057-945c-43d0-af7a-85fb565a0540"))
                .userId(UUID.fromString("78e74b34-8987-4962-8a31-51efcd15511d"))
                .placeId(firstPlaceUUID)
                .attachedPhotoIds(Collections.emptyList())
                .build();

    }

    @Test
    @DisplayName("콘텐트 길이가 1 이상이면 포인트 1점을 받는다.")
    public void Content_Length_Greater_Than_1_Gets_A_Point() {
        Integer contentLength = reviewAddService.checkContent(build);
        assertEquals(contentLength, 1);
    }

    @Test
    @DisplayName("이미지가 첨부되면 보너스 점수 1점을 받는다.")
    public void Attached_Photos_Gets_A_Point() {
        Integer images = ReviewUtils.checkImages(build);
        assertEquals(images, 1);
    }

    @Test
    @DisplayName("해당 장소의 첫번째 리뷰일 경우 보너스 포인트 1점을 받는다.")
    public void FIRST_REVIEW_GETS_BONUS_POINT() {

        Place place = placeRepository.findPlaceById(firstPlaceUUID);
        Set<Review> review = reviewRepository.findReviewByPlace(place);
        assertEquals(review.size(), 0);

        reviewAddService.transaction(placeFirstReview);

        List<Pointlog> pointLogs = pointLogRepository.findAll();
        List<Pointlog> lastPointLog = pointLogs.stream()
                .skip(pointLogs.size() - 1)
                .collect(Collectors.toList());

        final int BASIC_REVIEW_POINT = 1;
        final int FIRST_REVIEW_BONUS_POINT = 1;
        final int UPDATED_POINT = lastPointLog.get(0).getUpdatePoint();

        assertEquals(UPDATED_POINT, BASIC_REVIEW_POINT + FIRST_REVIEW_BONUS_POINT);
    }

    @Test
    @DisplayName("해당 장소의 리뷰가 이미 존재하면 보너스 점수를 부여하지 않는다.")
    public void Review_Already_Exists_No_Bonus_Point() {

        Review review1 = new Review();
        Review review2 = new Review();
        List<Review> reviews = new ArrayList<Review>() {{
            add(review1);
            add(review2);
        }};
        Integer isFirst = reviewAddService.checkFirstReview(reviews);
        assertEquals(isFirst, 0);
    }

    @Test
    @DisplayName("다양한 경우의 포인트를 계산한다.")
    public void Transact_Points() {

        int withFirstReviewBonus = reviewAddService.transactPoints(build);
        assertEquals(withFirstReviewBonus, 2);

        int withoutFirstReviewBonus = reviewAddService.transactPoints(build);
        assertEquals(withoutFirstReviewBonus, 2);

    }

}

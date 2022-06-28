package triple.mileage.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import triple.mileage.domain.Place;
import triple.mileage.domain.Review;
import triple.mileage.domain.User;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class ReviewRepositoryTest {

    @Autowired
    ReviewRepository reviewRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    PlaceRepository placeRepository;

//    @Test
    @Transactional
    public void test2() {
        Place place = placeRepository.findPlaceById(UUID.fromString("11ecf28c-462c-159d-bdc8-03fd26099cff"));
        Set<Review> review = reviewRepository.findReviewByPlace(place);
        for (Review review1 : review) {
            System.out.println("review1 = " + review1);
        }
    }


//    @Test
    @Transactional
    public void test() {
        User user = userRepository.findUserById(UUID.fromString("f9943a8f-fe37-4d7d-9511-e754b0f9f334"));
        Place place = placeRepository.findPlaceById(UUID.fromString("11ecf28c-462c-159d-bdc8-03fd26099cff"));
        Review review1 = Review.builder()
                .content("상생의손 리뷰2")
                .status("NORMAL")
                .user(user)
                .place(place)
                .build();
        reviewRepository.save(review1);

        List<Review> all = reviewRepository.findAll();
        for (Review review : all) {
            System.out.println("review = " + review);
            System.out.println(review.getId());
            System.out.println(review.getPlace());
        }
    }
}
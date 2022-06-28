package triple.mileage.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import triple.mileage.domain.Place;
import triple.mileage.domain.Review;
import triple.mileage.domain.User;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long>, QDSLReviewRepository {

    Set<Review> findReviewByPlace(Place place);


    // QueryDSL 로 status 가 true 인 것만 조회하도록 해야한다.
    Optional<Review> findReviewByUserAndPlace(User user, Place place);
    Optional<Review> findReviewByUserAndPlaceAndStatus(User user, Place place, String status);

//    @EntityGraph(attributePaths = {"photos"}) // (2)
    Review findReviewById(UUID reviewId);
    Optional<Review> findReview2ById(UUID reviewId);

}

package triple.mileage.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import triple.mileage.domain.Place;
import triple.mileage.domain.Review;

import java.util.List;
import java.util.UUID;

public interface QDSLReviewRepository {

    List<Review> findReviewByIdWithPhoto(UUID reviewId);
    List<Review> findReviewByPlaceAndStatus(Place place);
}

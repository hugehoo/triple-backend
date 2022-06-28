package triple.mileage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import triple.mileage.domain.Photo;
import triple.mileage.domain.Review;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Repository
public interface PhotoRepository extends JpaRepository<Photo, UUID> {

    Set<Photo> findPhotoByReview(Review review);

    List<Photo> findPhotoByReviewAndStatus(Review review, String status);

}

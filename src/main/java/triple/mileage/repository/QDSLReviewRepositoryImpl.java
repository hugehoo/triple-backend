package triple.mileage.repository;

import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import triple.mileage.domain.*;

import java.util.List;
import java.util.UUID;

public class QDSLReviewRepositoryImpl extends QuerydslRepositorySupport implements QDSLReviewRepository {
    public QDSLReviewRepositoryImpl() {
        super(Review.class);
    }

    @Override
    public List<Review> findReviewByIdWithPhoto(UUID reviewId) {
        QReview review = QReview.review;
        QPhoto photo = QPhoto.photo;

        return from(review)
                .where(review.id.eq(reviewId))
                .leftJoin(review.photos, photo)
                .fetchJoin()
                .where(photo.status.eq("NORMAL"))
                .distinct()
                .fetch();
    }

    @Override
    public List<Review> findReviewByPlaceAndStatus(Place place) {
        QReview review = QReview.review;

        return from(review)
                .where(review.place.eq(place))
                .where(review.status.eq("NORMAL"))
                .fetch();
    }
}

package triple.mileage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import triple.mileage.domain.Pointlog;

import java.util.List;

@Repository
public interface PointLogRepository extends JpaRepository<Pointlog, Long> {
    List<Pointlog> findByReviewId(String reviewId);
}

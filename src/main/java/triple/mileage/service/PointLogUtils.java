package triple.mileage.service;

import triple.mileage.domain.Place;
import triple.mileage.domain.Pointlog;
import triple.mileage.domain.Review;
import triple.mileage.domain.User;

import java.util.Map;

public class PointLogUtils {
    public static Pointlog pointLogBuilder(Review review, Map<String, Integer> points) {
        Place place1 = review.getPlace();
        User user1 = review.getUser();
        return Pointlog.builder()
                .userId(user1.getId().toString())
                .placeId(place1.getId().toString())
                .reviewId(review.getId().toString())
                .status("ADD")
                .updatePoint(points.get("additional"))
                .lastPoint(points.get("final"))
                .build();

    }
}

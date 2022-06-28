package triple.mileage.dto;


import ch.qos.logback.core.BasicStatusManager;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import triple.mileage.domain.Photo;
import triple.mileage.domain.Review;
import triple.mileage.domain.User;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDto {

    private UUID reviewId;
    private String content;
    private boolean firstReview;
    private UserDto user;

    public static ReviewDto of(Review review) {

        UserDto userDto = UserDto.builder()
                .userId(review.getUser().getId())
                .point(review.getUser().getPoint())
                .build();

        return ReviewDto.builder()
                .reviewId(review.getId())
                .content(review.getContent())
                .firstReview(review.isFirstReview())
                .user(userDto)
                .build();
    }

}




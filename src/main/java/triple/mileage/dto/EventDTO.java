package triple.mileage.dto;

import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.util.ObjectUtils;
import triple.mileage.domain.Photo;
import triple.mileage.domain.Place;
import triple.mileage.domain.Review;
import triple.mileage.domain.User;
import triple.mileage.repository.PlaceRepository;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@RequiredArgsConstructor
public class EventDTO {


    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class REVIEW {

        @NotNull
        private String type;

        @NotNull
        private String action;

        @NotNull
        private UUID reviewId;

        @Size(min = 1)
        private String content;

        private List<UUID> attachedPhotoIds;

        @NotNull
        private UUID userId;

        @NotNull
        private UUID placeId;

//        public Review toEntity(Place place, User user) {
        public Review toEntity(Place place, User user, int isFirst) {
            return Review.builder()
                    .id(this.reviewId)
                    .content(this.content)
                    .firstReview(isFirst > 0)
                    .status("NORMAL")
                    .place(place)
                    .user(user)
                    .build();
        }

    }
}

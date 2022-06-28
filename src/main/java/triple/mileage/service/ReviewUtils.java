package triple.mileage.service;

import triple.mileage.domain.Photo;
import triple.mileage.domain.Review;
import triple.mileage.dto.EventDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ReviewUtils {

    public static Integer checkImages(EventDTO.REVIEW reviewDto) {
        List<UUID> photoIds = reviewDto.getAttachedPhotoIds();
        if (photoIds != null && !photoIds.isEmpty()) {
            return 1;
        }
        return 0;
    }

    public static List<Photo> createImages(List<UUID> attachedPhotoIds, Review review) {
        List<Photo> photoBulk = new ArrayList<>();
        for (UUID attachedPhotoId : attachedPhotoIds) {
            Photo photo = Photo.builder()
                    .id(attachedPhotoId)
                    .status("NORMAL")
                    .review(review)
                    .build();
            photoBulk.add(photo);
        }
        return photoBulk;
    }

}

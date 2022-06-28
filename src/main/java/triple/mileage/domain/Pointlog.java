package triple.mileage.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
//@Audited
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Pointlog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userId;

    private String placeId;

    private String reviewId;

    private String status;

    private int updatePoint;

    private long lastPoint;


}

package triple.mileage.domain;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Photo {

//    @GeneratedValue(generator = "uuid2")
//    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Id
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id")
    private Review review;

    private String status;

}

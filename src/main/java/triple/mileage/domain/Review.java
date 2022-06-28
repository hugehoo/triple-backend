package triple.mileage.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

import java.util.List;
import java.util.UUID;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Review {


//    @Id
//    @Column(columnDefinition = "BINARY(16)")

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    private String content;

    @OneToMany(mappedBy = "review")
    private List<Photo> photos;

    private String status;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "place_id")
    @ToString.Exclude
    @JsonIgnore
    private Place place;

    public boolean firstReview;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    private User user;

//    @PrePersist
//    public void createUserUniqId() {
//        this.id = UUIDUtil.createUserUniqueId();
//    }
}

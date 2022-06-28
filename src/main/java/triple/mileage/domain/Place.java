package triple.mileage.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter // 이게 없으면 데이터 조회가 안되네.
@NoArgsConstructor
public class Place {

//    @Id
//    @Column(columnDefinition = "BINARY(16)")

    @Id // 이 옵션ㅅ들을 넣으면 @prepersist 를 사용하지 않아도 될까? -> yes
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(name = "name")
    private String name;

    public Place(String name) {
        this.name = name;
    }

    // 당장 필요 없는 필드 같아 보여.
//    @JsonIgnore
//    @OneToMany(fetch = LAZY)
//    private Set<Review> review;

//    @PrePersist
//    public void createUserUniqId() {
//        this.id = UUIDUtil.createUserUniqueId();
//    }
}

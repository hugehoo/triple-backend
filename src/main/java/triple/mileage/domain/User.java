package triple.mileage.domain;


import com.fasterxml.uuid.Generators;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
//@Audited
public class User {
//    @Id
//    @Column(columnDefinition = "BINARY(16)")
//    private UUID id;

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

//    @PrePersist
//    public void createUserUniqId() {
//        this.id = UUIDUtil.createUserUniqueId();
//    }

    private int point;

    public User(int point) {
        this.point = point;
    }

}

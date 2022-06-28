package triple.mileage.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import triple.mileage.domain.User;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;


//@SpringBootTest
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

//    @Test
    @Transactional
    public void test() {
        // given
//        User user1 = new User(10011L);
//        User user2 = new User(10012L);
//        userRepository.save(user1);
//        userRepository.save(user2);

        // when
        List<User> all = userRepository.findAll();
        for (User user : all) {
            System.out.println(user.getId());
        }

//        46026146-9cc9-44f0-b62c-e3e833fdc7d4
//        78e74b34-8987-4962-8a31-51efcd15511d

        // 성공
//        User userById = userRepository.findUserById(UUID.fromString("f9943a8f-fe37-4d7d-9511-e754b0f9f334"));

        // 실패 -> 적절한 UUID 형태가 아님
//        User userById = userRepository.findUserById(UUID.fromString("F9943A8FFE374D7D9511E754B0F9F334"));

        // 성공
//        User userById = userRepository.findUserById(UUID.fromString("F9943A8FFE374D7D9511E754B0F9F334"));


//        assertEquals(userById.getPoint(), 1002);

        // TODO : @PrePersist 하면서 어떤 작용 때문에, UUID 가 그대로 조회 되지 않는다.

    }

//    @Test
    public void getUserPoint() {

        UUID uuid = UUID.fromString("11ecf23f-b297-b1a9-a7e3-4f012c8a39cf");
        List<User> all = userRepository.findAll();
        for (User user : all) {  // 왜 DB 에 저장된 값이 그대로 안나오지?
            System.out.println(user.getId());
            System.out.println(user.getId().equals(uuid));
        }
//        User userById = userRepository.findUserById(uuid);
//        assertEquals(userById.getPoint(), 1003);

    }
}

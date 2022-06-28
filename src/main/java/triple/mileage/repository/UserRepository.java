package triple.mileage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import triple.mileage.domain.User;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findUserById(UUID userId);

}

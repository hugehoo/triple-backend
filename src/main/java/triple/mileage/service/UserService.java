package triple.mileage.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import triple.mileage.domain.User;
import triple.mileage.dto.UserDto;
import triple.mileage.repository.UserRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserDto getUserPoint(String userId) {
        User user = userRepository.findUserById(UUID.fromString(userId));
        return UserDto.builder()
                .userId(user.getId())
                .point(user.getPoint())
                .build();
    }

}

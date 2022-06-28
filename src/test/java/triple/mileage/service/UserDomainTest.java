package triple.mileage.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import triple.mileage.domain.User;
import triple.mileage.dto.UserDto;
import triple.mileage.repository.UserRepository;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserDomainTest {

    @Mock
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    UUID VALID_UUID;
    User USER;
    int POINT;
    String INVALID_UUID = "3fec5466-630f+4313_bbea-0fff58819836";

    @BeforeEach
    public void setupUser() {
        POINT = 1000;
        USER = new User(POINT);
        VALID_UUID = UUID.randomUUID();
        USER.setId(VALID_UUID);

//        INVALID_UUID = UUID.fromString(VALID_UUID + "1");
    }

//    @Test
    @DisplayName("UserService UserDTO 테스트")
    public void UserDtoTest() throws Exception {

        // when
        String stringUUID = VALID_UUID.toString();

        UserDto userDto = UserDto.builder()
                .userId(VALID_UUID)
                .point(POINT)
                .build();

        when(userService.getUserPoint(stringUUID)).thenReturn(userDto);

        // then
        assertEquals(userService.getUserPoint(stringUUID).getPoint(), POINT);

    }

//    @Test
    @DisplayName("UUID로 유저를 조회한다.")
    public void test() throws Exception {

        // when
        when(userRepository.findUserById(VALID_UUID)).thenReturn(USER);

        // then
        assertEquals(userRepository.findUserById(VALID_UUID), USER);
        assertEquals(userRepository.findUserById(VALID_UUID).getPoint(), USER.getPoint());
    }

}
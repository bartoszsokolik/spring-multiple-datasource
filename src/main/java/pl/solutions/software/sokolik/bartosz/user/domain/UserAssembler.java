package pl.solutions.software.sokolik.bartosz.user.domain;

import org.springframework.stereotype.Component;
import pl.solutions.software.sokolik.bartosz.user.dto.UserDto;

@Component
class UserAssembler {

    User fromDto(UserDto dto) {
        return User.builder()
                .username(dto.getUsername())
                .password(dto.getPassword())
                .email(dto.getEmail())
                .role(UserRole.valueOf(dto.getRole()))
                .build();
    }

    UserDto fromDomain(User user) {
        return UserDto.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .email(user.getEmail())
                .role(user.getRole().toString())
                .build();
    }
}

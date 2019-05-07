package pl.solutions.software.sokolik.bartosz.user.dto;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class UserDto {

    private String username;

    private String password;

    private String email;

    private String role;
}

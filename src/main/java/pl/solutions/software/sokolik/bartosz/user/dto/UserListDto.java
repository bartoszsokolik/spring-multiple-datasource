package pl.solutions.software.sokolik.bartosz.user.dto;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class UserListDto {

    private List<UserDto> users;
}

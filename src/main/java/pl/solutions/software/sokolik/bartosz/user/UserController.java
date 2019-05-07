package pl.solutions.software.sokolik.bartosz.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.solutions.software.sokolik.bartosz.user.domain.UserFacade;
import pl.solutions.software.sokolik.bartosz.user.dto.UserDto;
import pl.solutions.software.sokolik.bartosz.user.dto.UserListDto;

import java.net.URI;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
class UserController {

    private final UserFacade userFacade;

    @GetMapping
    ResponseEntity<UserListDto> findAll() {
        UserListDto userListDto = UserListDto.builder()
                .users(userFacade.findAll())
                .build();
        return new ResponseEntity<>(userListDto, HttpStatus.OK);
    }

    @PostMapping
    ResponseEntity<Void> addUser(@RequestBody UserDto dto) {
        Long userId = userFacade.addUser(dto);
        URI location = fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(userId)
                .toUri();

        return ResponseEntity.created(location).build();
    }
}

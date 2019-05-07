package pl.solutions.software.sokolik.bartosz.user.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.solutions.software.sokolik.bartosz.user.dto.UserDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional("userTransactionManager")
public class UserFacade {

    private final UserRepository userRepository;
    private final UserAssembler userAssembler;

    public List<UserDto> findAll() {
        return userRepository.findAll()
                .stream()
                .map(userAssembler::fromDomain)
                .collect(Collectors.toList());
    }

    public Long addUser(UserDto dto) {
        User user = userAssembler.fromDto(dto);
        return userRepository.save(user).getId();
    }
}

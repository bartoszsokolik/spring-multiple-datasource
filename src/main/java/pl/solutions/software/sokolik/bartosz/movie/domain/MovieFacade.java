package pl.solutions.software.sokolik.bartosz.movie.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.solutions.software.sokolik.bartosz.movie.dto.MovieDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional("movieTransactionManager")
public class MovieFacade {

    private final MovieRepository movieRepository;
    private final MovieAssembler movieAssembler;

    public List<MovieDto> findAll() {
        return movieRepository.findAll()
                .stream()
                .map(movieAssembler::fromDomain)
                .collect(Collectors.toList());
    }

    public Long createMovie(MovieDto dto) {
        Movie movie = movieAssembler.fromDto(dto);
        return movieRepository.save(movie).getId();
    }
}

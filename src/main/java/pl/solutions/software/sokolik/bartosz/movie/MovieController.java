package pl.solutions.software.sokolik.bartosz.movie;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.solutions.software.sokolik.bartosz.movie.domain.MovieFacade;
import pl.solutions.software.sokolik.bartosz.movie.dto.MovieDto;
import pl.solutions.software.sokolik.bartosz.movie.dto.MovieListDto;

import java.net.URI;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

@RestController
@RequestMapping("/api/movie")
@RequiredArgsConstructor
public class MovieController {

    private final MovieFacade movieFacade;

    @GetMapping
    ResponseEntity<MovieListDto> findAll() {
        MovieListDto dto = MovieListDto.builder()
                .movies(movieFacade.findAll())
                .build();
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Void> addMovie(@RequestBody MovieDto dto) {
        Long movieId = movieFacade.createMovie(dto);

        URI location = fromCurrentRequest().path("/{id}")
                .buildAndExpand(movieId)
                .toUri();

        return ResponseEntity.created(location).build();
    }
}

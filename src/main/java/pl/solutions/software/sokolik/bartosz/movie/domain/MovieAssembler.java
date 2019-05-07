package pl.solutions.software.sokolik.bartosz.movie.domain;

import org.springframework.stereotype.Component;
import pl.solutions.software.sokolik.bartosz.movie.dto.MovieDto;

@Component
public class MovieAssembler {

    Movie fromDto(MovieDto dto) {
        return Movie.builder()
                .title(dto.getTitle())
                .build();
    }

    MovieDto fromDomain(Movie movie) {
        return MovieDto.builder()
                .title(movie.getTitle())
                .build();
    }
}

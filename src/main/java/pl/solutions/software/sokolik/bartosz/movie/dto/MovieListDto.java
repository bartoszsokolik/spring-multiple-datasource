package pl.solutions.software.sokolik.bartosz.movie.dto;


import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class MovieListDto {

    private List<MovieDto> movies;
}

package pl.solutions.software.sokolik.bartosz.movie.domain;

import org.springframework.data.jpa.repository.JpaRepository;

interface MovieRepository extends JpaRepository<Movie, Long> {

}

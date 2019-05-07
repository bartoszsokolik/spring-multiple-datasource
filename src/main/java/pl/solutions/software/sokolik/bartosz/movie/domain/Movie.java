package pl.solutions.software.sokolik.bartosz.movie.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "movies")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter(AccessLevel.PACKAGE)
class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;
}

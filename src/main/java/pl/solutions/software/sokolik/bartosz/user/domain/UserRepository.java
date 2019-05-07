package pl.solutions.software.sokolik.bartosz.user.domain;

import org.springframework.data.jpa.repository.JpaRepository;

interface UserRepository extends JpaRepository<User, Long> {
}

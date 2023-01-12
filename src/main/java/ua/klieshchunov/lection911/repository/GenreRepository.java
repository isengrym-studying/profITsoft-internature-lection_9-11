package ua.klieshchunov.lection911.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.klieshchunov.lection911.entity.Genre;

public interface GenreRepository extends JpaRepository<Genre, Integer> {
}

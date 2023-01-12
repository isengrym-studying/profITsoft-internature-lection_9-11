package ua.klieshchunov.lection911.service;

import ua.klieshchunov.lection911.entity.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreService {
    List<Genre> getAllGenres();
    Optional<Genre> findById(Integer id);
}

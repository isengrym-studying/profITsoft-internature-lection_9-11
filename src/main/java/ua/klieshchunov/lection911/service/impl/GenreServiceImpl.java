package ua.klieshchunov.lection911.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.klieshchunov.lection911.entity.Genre;
import ua.klieshchunov.lection911.repository.GenreRepository;
import ua.klieshchunov.lection911.service.GenreService;

import java.util.List;
import java.util.Optional;

@Service
public class GenreServiceImpl implements GenreService {
    private final GenreRepository genreRepo;

    @Autowired
    public GenreServiceImpl(GenreRepository genreRepo) {
        this.genreRepo = genreRepo;
    }

    @Override
    public List<Genre> getAllGenres() {
        return genreRepo.findAll();
    }

    @Override
    public Optional<Genre> findById(Integer id) {
        if (id == null)
            return Optional.empty();

        return genreRepo.findById(id);
    }
}

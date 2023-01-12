package ua.klieshchunov.lection911.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.klieshchunov.lection911.entity.Genre;
import ua.klieshchunov.lection911.service.GenreService;

import java.util.List;

@Controller
@RequestMapping("/genres")
public class GenreController {
    private final GenreService genreService;

    @Autowired
    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping
    public ResponseEntity<List<Genre>> getAllBooks() {
        return new ResponseEntity<>(genreService.getAllGenres(), HttpStatus.OK);
    }

}

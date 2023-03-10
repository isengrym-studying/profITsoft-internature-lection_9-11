package ua.klieshchunov.lection911.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import ua.klieshchunov.lection911.entity.Book;
import ua.klieshchunov.lection911.entity.Genre;
import ua.klieshchunov.lection911.service.exception.BookAlreadyExistsException;
import ua.klieshchunov.lection911.service.exception.BookNotFoundException;

import java.util.Optional;


public interface BookService {
    Page<Book> getAllBooks(Specification<Book> specification, Pageable pageable);
    Specification<Book> buildGenreAuthorSpecification(Genre genre, String author);
    Optional<Book> findById(int id);
    Book save(Book book) throws BookAlreadyExistsException;
    Book update(Book book) throws BookNotFoundException;
    void delete(Book book);
}

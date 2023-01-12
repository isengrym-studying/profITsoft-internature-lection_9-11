package ua.klieshchunov.lection911.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ua.klieshchunov.lection911.entity.Book;
import ua.klieshchunov.lection911.service.exception.BookAlreadyExistsException;
import ua.klieshchunov.lection911.service.exception.BookNotFoundException;

import java.util.Optional;


public interface BookService {
    Page<Book> getAllBooks(Pageable pageable);
    Optional<Book> findById(int id);
    Book save(Book book) throws BookAlreadyExistsException;
    Book update(Book book) throws BookNotFoundException;
    void delete(Book book);
}

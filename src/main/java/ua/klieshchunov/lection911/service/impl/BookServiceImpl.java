package ua.klieshchunov.lection911.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ua.klieshchunov.lection911.entity.Book;
import ua.klieshchunov.lection911.entity.Genre;
import ua.klieshchunov.lection911.repository.BookRepository;
import ua.klieshchunov.lection911.service.BookService;
import ua.klieshchunov.lection911.service.exception.BookAlreadyExistsException;
import ua.klieshchunov.lection911.service.exception.BookNotFoundException;
import ua.klieshchunov.lection911.service.specification.BookByAuthorSpecification;
import ua.klieshchunov.lection911.service.specification.BookByGenreSpecification;

import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepo;

    @Autowired
    public BookServiceImpl(BookRepository bookRepo) {
        this.bookRepo = bookRepo;
    }

    @Override
    public Optional<Book> findById(int id) {
        return bookRepo.findById(id);
    }

    @Override
    public Page<Book> getAllBooks(Specification<Book> specification, Pageable pageable) {
        return bookRepo.findAll(specification, pageable);
    }

    @Override
    public Specification<Book> buildGenreAuthorSpecification(Genre genre, String author) {
        Specification<Book> specificationGenre = (root, query, cb) -> cb.and();
        Specification<Book> specificationAuthor = (root, query, cb) -> cb.and();

        if (genre != null)
            specificationGenre = Specification.where(new BookByGenreSpecification(genre));

        if (author != null)
            specificationAuthor = Specification.where(new BookByAuthorSpecification(author));

        return Specification.where(specificationGenre).and(Specification.where(specificationAuthor));
    }

    @Override
    public Book save(Book book) throws BookAlreadyExistsException {
        if (bookExistsById(book.getId()))
            throw new BookAlreadyExistsException(String.format("Book with id='%s' already exists", book.getId()));

        return bookRepo.save(book);
    }

    @Override
    public Book update(Book book) throws BookNotFoundException {
        if (!bookExistsById(book.getId()))
            throw new BookNotFoundException(String.format("Couldn't find book with id='%s'",book.getId()));

        return bookRepo.save(book);
    }

    @Override
    public void delete(Book book) {
        bookRepo.delete(book);
    }

    private boolean bookExistsById(int id) {
        Optional<Book> bookOpt = findById(id);
        return bookOpt.isPresent();
    }
}

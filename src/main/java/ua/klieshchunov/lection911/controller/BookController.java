package ua.klieshchunov.lection911.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.klieshchunov.lection911.entity.Book;
import ua.klieshchunov.lection911.entity.Genre;
import ua.klieshchunov.lection911.service.BookService;
import ua.klieshchunov.lection911.service.GenreService;
import ua.klieshchunov.lection911.service.exception.BookAlreadyExistsException;
import ua.klieshchunov.lection911.service.exception.BookNotFoundException;
import ua.klieshchunov.lection911.service.exception.BookValidationException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;
    private final GenreService genreService;

    @Autowired
    public BookController(BookService bookService, GenreService genreService) {
        this.bookService = bookService;
        this.genreService = genreService;
    }

    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks(@RequestParam(value = "pageSize", defaultValue = "5") int pageSize,
                                                  @RequestParam(value = "pageNum", defaultValue = "0") int pageNum,
                                                  @RequestParam(value = "genreId", required=false) Integer genreId,
                                                  @RequestParam(value = "author", required=false) String author) {

        Optional<Genre> genre = genreService.findById(genreId);
        Specification<Book> specification = bookService.buildGenreAuthorSpecification(genre.orElse(null), author);
        Pageable pageable = PageRequest.of(pageNum, pageSize);

        return new ResponseEntity<>(bookService.getAllBooks(specification, pageable).getContent(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getCertainBook(@PathVariable("id") int id) throws BookNotFoundException {
        Optional<Book> bookOpt = bookService.findById(id);
        return bookOpt.map(book -> new ResponseEntity<>(book, HttpStatus.OK))
                .orElseThrow(() -> new BookNotFoundException(String.format("Couldn't find book with id='%s'", id)));
    }

    @PostMapping("/add")
    public ResponseEntity<Book> addNewBook(@RequestBody @Valid Book book,
                                           BindingResult bindingResult) throws BookAlreadyExistsException, BookValidationException {
        throwExceptionIfHasErrors(bindingResult);
        return new ResponseEntity<>(bookService.save(book), HttpStatus.CREATED);
    }

    @PatchMapping("/update")
    public ResponseEntity<Book> updateBook(@RequestBody @Valid Book book,
                                           BindingResult bindingResult) throws BookNotFoundException, BookValidationException {
        throwExceptionIfHasErrors(bindingResult);
        return new ResponseEntity<>(bookService.update(book), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public HttpStatus deleteBook(@PathVariable("id") int id) {
        Optional<Book> bookOpt = bookService.findById(id);
        bookOpt.ifPresent(bookService::delete);
        return HttpStatus.OK;
    }

    private void throwExceptionIfHasErrors(BindingResult bindingResult) throws BookValidationException {
        if (bindingResult.hasErrors()) {
            StringBuilder message = new StringBuilder();

            bindingResult.getFieldErrors()
                    .forEach(fieldError -> message
                            .append(fieldError.getDefaultMessage())
                            .append("; "));

            throw new BookValidationException(message.toString());
        }
    }
}

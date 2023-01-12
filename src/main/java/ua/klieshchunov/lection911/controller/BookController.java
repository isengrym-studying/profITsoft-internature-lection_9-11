package ua.klieshchunov.lection911.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.klieshchunov.lection911.entity.Book;
import ua.klieshchunov.lection911.service.BookService;
import ua.klieshchunov.lection911.service.exception.BookAlreadyExistsException;
import ua.klieshchunov.lection911.service.exception.BookNotFoundException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping
    public ResponseEntity<List<Book>> getAllBooks(@RequestParam(value = "pageSize", defaultValue = "5") int pageSize,
                                                  @RequestParam(value = "pageNum", defaultValue = "0") int pageNum) {
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        return new ResponseEntity<>(bookService.getAllBooks(pageable).getContent(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getCertainBook(@PathVariable("id") int id) {
        Optional<Book> bookOpt = bookService.findById(id);
        return bookOpt.map(book -> new ResponseEntity<>(book, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }

    @PostMapping("/add")
    public ResponseEntity<Book> addNewBook(@RequestBody Book book) throws BookAlreadyExistsException {
        return new ResponseEntity<>(bookService.save(book), HttpStatus.CREATED);
    }

    @PatchMapping("/update")
    public ResponseEntity<Book> updateBook(@RequestBody Book book) throws BookNotFoundException {
        return new ResponseEntity<>(bookService.update(book), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public HttpStatus deleteBook(@PathVariable("id") int id) {
        Optional<Book> bookOpt = bookService.findById(id);
        bookOpt.ifPresent(bookService::delete);
        return HttpStatus.OK;
    }



}

package ua.klieshchunov.lection911.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ua.klieshchunov.lection911.entity.Book;

public interface BookRepository extends JpaRepository<Book, Integer>, JpaSpecificationExecutor<Book> {
    Page<Book> findAll(Pageable pageable);
}

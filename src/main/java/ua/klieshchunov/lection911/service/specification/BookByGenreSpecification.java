package ua.klieshchunov.lection911.service.specification;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import ua.klieshchunov.lection911.entity.Book;
import ua.klieshchunov.lection911.entity.Book_;
import ua.klieshchunov.lection911.entity.Genre;

public class BookByGenreSpecification implements Specification<Book> {
    private final Genre genre;

    public BookByGenreSpecification(Genre genre) {
        this.genre = genre;
    }

    @Override
    public Predicate toPredicate(Root<Book> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        if (genre != null)
            return criteriaBuilder.equal(root.get(Book_.genre), genre);
        else
            return criteriaBuilder.and();
    }
}

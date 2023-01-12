package ua.klieshchunov.lection911.service.specification;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import ua.klieshchunov.lection911.entity.Book;
import ua.klieshchunov.lection911.entity.Book_;

public class BookByAuthorSpecification implements Specification<Book> {
    private final String author;

    public BookByAuthorSpecification(String author) {
        this.author = author;
    }

    @Override
    public Predicate toPredicate(Root<Book> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        if (author != null && !author.isEmpty())
            return criteriaBuilder.equal(root.get(Book_.author), author);
        else
            return criteriaBuilder.and();
    }
}

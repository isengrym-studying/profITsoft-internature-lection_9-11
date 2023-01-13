package ua.klieshchunov.lection911.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Objects;

@Entity
@Table(name="book")
@Data
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="book_id")
    private int id;

    @Column(name="title")
    @Size(max = 150, message = "Book title too long")
    private String title;

    @Size(max = 150, message = "Author name too long")
    @Column(name="author")
    private String author;

    @ManyToOne
    @JoinColumn(name="genre")
    private Genre genre;

    @Min(value = 1, message = "Number of pages cannot be less than 1")
    @Column(name="pages_number")
    private int pagesNumber;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return id == book.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

package ua.klieshchunov.lection911.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="book")
@Data
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="book_id")
    private int id;

    @Column(name="title")
    private String title;

    @Column(name="author")
    private String author;

    @ManyToOne
    @JoinColumn(name="genre")
    private Genre genre;

    @Column(name="pages_number")
    private int pagesNumber;
}

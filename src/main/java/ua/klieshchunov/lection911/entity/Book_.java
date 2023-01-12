package ua.klieshchunov.lection911.entity;

import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Book.class)
public class Book_ {
    public static volatile SingularAttribute<Book, Integer> id;
    public static volatile SingularAttribute<Book, String> title;
    public static volatile SingularAttribute<Book, String> author;
    public static volatile SingularAttribute<Book, Genre> genre;
    public static volatile SingularAttribute<Book, Integer> pagesNumber;
}

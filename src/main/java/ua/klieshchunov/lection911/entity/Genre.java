package ua.klieshchunov.lection911.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="genre")
@Data
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="genre_id")
    private int id;

    @Column(name="name")
    private String name;
}

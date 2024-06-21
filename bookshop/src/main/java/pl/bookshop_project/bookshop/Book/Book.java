package pl.bookshop_project.bookshop.Book;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import lombok.Data;
import org.hibernate.annotations.UuidGenerator;
import pl.bookshop_project.bookshop.Author.Author;

import java.util.UUID;

@Data
@Entity
public class Book {
    @Id
    @UuidGenerator
    private UUID id;
    private String title;
    private String genre;
    private double price;
    private int pages;
    private int viewsCounter = 0;
    private int quantity;
    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;
}

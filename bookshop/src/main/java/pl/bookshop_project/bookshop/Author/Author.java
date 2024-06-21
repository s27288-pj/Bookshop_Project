package pl.bookshop_project.bookshop.Author;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
public class Author {
    @Id
    private UUID id;
    private String name;
    private String surname;
}

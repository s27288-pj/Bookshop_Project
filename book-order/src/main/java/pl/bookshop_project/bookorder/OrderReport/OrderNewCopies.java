package pl.bookshop_project.bookorder.OrderReport;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Data
public class OrderNewCopies {
    @Id
    @UuidGenerator
    private UUID orderId;
    private UUID bookId;
    private Integer numberOfVisits = 0;
}

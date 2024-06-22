package pl.bookshop_project.bookshop.Book;

import com.bookapi.openapi.model.OrderReport;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.bookapi.openapi.model.BookCreateRequest;
import com.bookapi.openapi.model.BookResponse;
import com.bookapi.openapi.model.BookUpdateRequest;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    @PostMapping
    public ResponseEntity<BookResponse> createBook(BookCreateRequest bookCreateRequest) {
        return bookService.createBook(bookCreateRequest);
    }

    @GetMapping
    public ResponseEntity<List<BookResponse>> getBook() {
        return bookService.getBook();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookResponse> getBook(UUID id) {
        return bookService.getBook(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookResponse> updateBook(UUID id, BookUpdateRequest bookUpdateRequest) {
        return bookService.updateBook(id, bookUpdateRequest);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(UUID id) {
        return bookService.deleteBook(id);
    }

    @GetMapping("/search/{genre}")
    public ResponseEntity<List<BookResponse>> getBookByGenre(String genre) {
        return bookService.getBookByGenre(genre);
    }

    @GetMapping("/order-book")
    public ResponseEntity<List<OrderReport>> sendOrderReport() {
        return bookService.sendOrderReport();
    }
}

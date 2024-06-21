package pl.bookshop_project.bookshop.Book;

import com.bookapi.openapi.model.BookCreateRequest;
import com.bookapi.openapi.model.BookResponse;
import com.bookapi.openapi.model.BookUpdateRequest;
import com.bookapi.openapi.model.OrderReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import pl.bookshop_project.bookshop.Author.AuthorRepository;
import pl.bookshop_project.bookshop.Author.Author;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import pl.bookshop_project.bookshop.Exception.InvalidCreateBookDataException;
import pl.bookshop_project.bookshop.OrderReportClient;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final BookMapper bookMapper;
    @Autowired
    private final OrderReportClient orderReportClient;

    public ResponseEntity<BookResponse> createBook(BookCreateRequest bookCreateRequest) {
        verifyBookCreateFields(bookCreateRequest);
        Book book = bookMapper.toBook(bookCreateRequest);
        Author author = authorRepository.findById(bookCreateRequest.getAuthorId()).orElseThrow();
        book.setAuthor(author);

        bookRepository.save(book);
        return ResponseEntity.ok(bookMapper.toBookResponse(book));
    }

    public ResponseEntity<BookResponse> updateBook(UUID id, BookUpdateRequest bookUpdateRequest) {
        verifyBookUpdateFields(bookUpdateRequest);
        Book book = bookRepository.findById(id).orElseThrow();
        bookMapper.toUpdate(book, bookUpdateRequest);
        bookRepository.save(book);
        return ResponseEntity.ok(bookMapper.toBookResponse(book));
    }

    public ResponseEntity<List<BookResponse>> getBook() {
        return ResponseEntity.ok(bookRepository.findAll().stream().map(bookMapper::toBookResponse).toList());
    }

    public ResponseEntity<BookResponse> getBook(UUID id) {
        verifyBookId(id);
        Book book = bookRepository.findById(id).orElseThrow();
        return ResponseEntity.ok(bookMapper.toBookResponse(book));
    }

    public ResponseEntity<BookResponse> deleteBook(UUID id) {
        verifyBookId(id);
        Book book = bookRepository.findById(id).orElseThrow();
        bookRepository.delete(book);
        return ResponseEntity.ok(bookMapper.toBookResponse(book));
    }

    public ResponseEntity<List<BookResponse>> getBookByGenre(String genre) {
        return ResponseEntity.ok(bookRepository.findBooksbyGenre(genre).stream().map(bookMapper::toBookResponse).toList());
    }

    public ResponseEntity<List<OrderReport>> sendOrderReport() {
        return orderReportClient.sendOrderReport(bookRepository.findAll().stream().map(bookMapper::toOrderNewCopies).toList());
    }

    // Validate fields
    private void verifyBookCreateFields(BookCreateRequest bookCreateRequest) {
        if (bookCreateRequest.getTitle() == null || bookCreateRequest.getTitle().isEmpty()) {
            throw new InvalidCreateBookDataException("Title is required");
        }
        if (bookCreateRequest.getAuthorId() == null) {
            throw new InvalidCreateBookDataException("Author ID is required");
        }
    }

    private void verifyBookUpdateFields(BookUpdateRequest bookUpdateRequest) {
        if (bookUpdateRequest.getTitle() == null || bookUpdateRequest.getTitle().isEmpty()) {
            throw new InvalidCreateBookDataException("Title is required");
        }
    }

    private void verifyBookId(UUID id) {
        if (!bookRepository.existsById(id)) {
            throw new InvalidCreateBookDataException("Book with ID " + id + " does not exist");
        }
    }
}

package pl.bookshop_project.bookshop;

import com.bookapi.openapi.model.BookCreateRequest;
import com.bookapi.openapi.model.BookResponse;
import com.bookapi.openapi.model.BookUpdateRequest;
import com.bookapi.openapi.model.Author;
import pl.bookshop_project.bookshop.Author.*;
import pl.bookshop_project.bookshop.Book.*;
import pl.bookshop_project.bookshop.Exception.InvalidCreateBookDataException;
import pl.bookshop_project.bookshop.Exception.InvalidAuthorIdException;
import pl.bookshop_project.bookshop.Exception.InvalidBookIdException;
import pl.bookshop_project.bookshop.OrderReportClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class BookshopApplicationTests {

    @InjectMocks
    private BookService bookService;

    @Mock
    private BookMapper bookMapper;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private OrderReportClient orderReportClient;

    private Book book;
    private BookCreateRequest bookCreateRequest;
    private BookUpdateRequest bookUpdateRequest;
    private BookResponse bookResponse;
    private UUID bookId;

    private pl.bookshop_project.bookshop.Author.Author author;
    private com.bookapi.openapi.model.Author responseAuthor;
    private UUID authorId;

    @BeforeEach
    public void setUp() {

        authorId = UUID.randomUUID();
        author = new pl.bookshop_project.bookshop.Author.Author();
        author.setId(authorId);
        author.setName("FirstName");
        author.setSurname("LastName");

        bookCreateRequest = new BookCreateRequest();
        bookCreateRequest.setTitle("Title");
        bookCreateRequest.setGenre("Genre");
        bookCreateRequest.setPages(100);
        bookCreateRequest.setPrice(10.0);
        bookCreateRequest.setAuthorId(authorId);

        bookUpdateRequest = new BookUpdateRequest();
        bookUpdateRequest.setTitle("Updated Title");
        bookUpdateRequest.setGenre("Updated Genre");
        bookUpdateRequest.setPages(200);
        bookUpdateRequest.setPrice(20.0);
        bookUpdateRequest.setAuthorId(authorId);

        bookId = UUID.randomUUID();
        book = new Book();
        book.setId(bookId);
        book.setTitle("Title");
        book.setGenre("Genre");
        book.setPages(100);
        book.setPrice(10.0);
        book.setAuthor(author);

        responseAuthor = new Author();

        bookResponse = new BookResponse();
        bookResponse.setId(bookId);
        bookResponse.setTitle("Title");
        bookResponse.setGenre("Genre");
        bookResponse.setPages(100);
        bookResponse.setPrice(10.0);
        bookResponse.setAuthor(responseAuthor);
    }

    @Test
    public void createBookTest() throws InvalidCreateBookDataException, InvalidAuthorIdException {
        when(authorRepository.findById(authorId)).thenReturn(java.util.Optional.of(author));
        when(bookMapper.toBook(any(BookCreateRequest.class))).thenReturn(book);
        when(bookRepository.save(any(Book.class))).thenReturn(book);
        when(bookMapper.toBookResponse(any(Book.class))).thenReturn(bookResponse);

        ResponseEntity<BookResponse> response = bookService.createBook(bookCreateRequest);

        assertNotNull(response);
        assertEquals(bookResponse, response.getBody());
    }

    @Test
    public void createInvalidBookTest() throws InvalidCreateBookDataException, InvalidAuthorIdException {
        bookCreateRequest.setAuthorId(null);
        assertThrows(InvalidCreateBookDataException.class, () -> bookService.createBook(bookCreateRequest));
    }

    @Test
    public void deleteBookTest() throws InvalidBookIdException {
        when(bookRepository.existsById(bookId)).thenReturn(true);
        when(bookRepository.findById(bookId)).thenReturn(java.util.Optional.of(book));

        ResponseEntity<Void> response = bookService.deleteBook(bookId);

        assertNotNull(response);
        assertEquals(204, response.getStatusCodeValue());
    }

    @Test
    public void deleteInvalidBookTest() throws InvalidBookIdException {
        when(bookRepository.existsById(bookId)).thenReturn(false);

        assertThrows(InvalidBookIdException.class, () -> bookService.deleteBook(bookId));
    }

    @Test
    public void updateBookTest() throws InvalidCreateBookDataException, InvalidBookIdException {
        when(bookRepository.findById(bookId)).thenReturn(java.util.Optional.of(book));
        when(bookMapper.toUpdate(any(Book.class), any(BookUpdateRequest.class))).thenReturn(book);
        when(bookRepository.save(any(Book.class))).thenReturn(book);
        when(bookMapper.toBookResponse(any(Book.class))).thenReturn(bookResponse);

        ResponseEntity<BookResponse> response = bookService.updateBook(bookId, bookUpdateRequest);

        assertNotNull(response);
        assertEquals(bookResponse, response.getBody());
    }

    @Test
    public void getBooksTest() {
        when(bookRepository.findAll()).thenReturn(List.of(book));
        when(bookMapper.toBookResponse(book)).thenReturn(bookResponse);

        ResponseEntity<List<BookResponse>> response = bookService.getBook();

        assertNotNull(response);
        assertEquals(List.of(bookResponse), response.getBody());
    }

    @Test
    public void getBookByIdTest() throws InvalidBookIdException {
        when(bookRepository.findById(bookId)).thenReturn(java.util.Optional.of(book));
        when(bookRepository.existsById(bookId)).thenReturn(true);
        when(bookMapper.toBookResponse(book)).thenReturn(bookResponse);

        ResponseEntity<BookResponse> response = bookService.getBook(bookId);

        assertNotNull(response);
        assertEquals(bookResponse, response.getBody());
    }

    @Test
    public void getBookInvalidIdTest() throws InvalidBookIdException {
        when(bookRepository.existsById(bookId)).thenReturn(false);

        assertThrows(InvalidBookIdException.class, () -> bookService.getBook(bookId));
    }

    @Test
    public void getBookByGenreTest() {
        when(bookRepository.findBooksbyGenre("Genre")).thenReturn(List.of(book));
        when(bookMapper.toBookResponse(book)).thenReturn(bookResponse);

        ResponseEntity<List<BookResponse>> response = bookService.getBookByGenre("Genre");

        assertNotNull(response);
        assertEquals(List.of(bookResponse), response.getBody());
    }
}

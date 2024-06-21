package pl.bookshop_project.bookshop.Book;

import org.mapstruct.*;

import com.bookapi.openapi.model.BookCreateRequest;
import com.bookapi.openapi.model.BookResponse;
import com.bookapi.openapi.model.BookUpdateRequest;
import pl.bookshop_project.bookorder.OrderReport.OrderNewCopies;

@Mapper(componentModel = "spring")
public interface BookMapper {

    @Mapping(target = "id", ignore = true)
    Book toBook(BookCreateRequest bookCreateRequest);

    BookResponse toBookResponse(Book book);

    @Mapping(target = "id", ignore = true)
    Book toUpdate(@MappingTarget Book book, BookUpdateRequest bookUpdateRequest);

    @Mapping(target = "bookId", ignore = true, source = "id")
    OrderNewCopies toOrderNewCopies(Book book);

}

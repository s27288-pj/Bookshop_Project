package pl.bookshop_project.bookshop.Exception;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class BookshopExceptionHandler {

    Logger logger = LogManager.getLogger(BookshopExceptionHandler.class);

    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Book not found")
    @ExceptionHandler(InvalidBookIdException.class)
    public void handleBookNotFound(InvalidBookIdException ex) {
        logger.warn("Book not found: " + ex.getMessage());
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Author not found")
    @ExceptionHandler(InvalidAuthorIdException.class)
    public void handleAuthorNotFound(InvalidAuthorIdException ex) {
        logger.warn("Author not found: " + ex.getMessage());
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Invalid book fields")
    @ExceptionHandler(InvalidCreateBookDataException.class)
    public void handleInvalidBookFields(InvalidCreateBookDataException ex) {
        logger.warn("Invalid book fields: " + ex.getMessage());
    }
}

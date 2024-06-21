package pl.bookshop_project.bookshop.Exception;

public class InvalidBookIdException extends RuntimeException {
    public InvalidBookIdException() {
    }

    public InvalidBookIdException(String message) {
        super(message);
    }
}

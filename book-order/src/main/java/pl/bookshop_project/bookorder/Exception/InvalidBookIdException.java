package pl.bookshop_project.bookorder.Exception;

public class InvalidBookIdException extends RuntimeException {
    public InvalidBookIdException() {
    }

    public InvalidBookIdException(String message) {
        super(message);
    }
}

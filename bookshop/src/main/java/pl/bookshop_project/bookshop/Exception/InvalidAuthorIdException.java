package pl.bookshop_project.bookshop.Exception;

public class InvalidAuthorIdException extends RuntimeException {
    public InvalidAuthorIdException() {
    }

    public InvalidAuthorIdException(String message) {
        super(message);
    }
}

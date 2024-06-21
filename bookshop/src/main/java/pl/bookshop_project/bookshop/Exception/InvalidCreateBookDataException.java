package pl.bookshop_project.bookshop.Exception;

public class InvalidCreateBookDataException extends RuntimeException {
    public InvalidCreateBookDataException() {
    }

    public InvalidCreateBookDataException(String message) {
        super(message);
    }
}

package pl.bookshop_project.bookorder.Exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class BookOrderExceptionHandler {
    Logger logger = LogManager.getLogger(BookOrderExceptionHandler.class);

    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Order not found")
    public void handleOrderNotFoundException() {
        logger.error("Order not found");
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Order report not found")
    public void handleOrderReportNotFoundException() {
        logger.error("Order report not found");
    }
}

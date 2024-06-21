package pl.bookshop_project.bookorder.OrderReport;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order-report")
public class OrderController {
    @Autowired
    private final OrderService orderService;

    @PostMapping ("/sendOrderReport")
    ResponseEntity<List<OrderReport>> sendOrderReport(@RequestBody List<OrderNewCopies> orderReports) {
        return orderService.receiveOrderReport(orderReports);
    }

    @GetMapping("/exportOrderReport")
    public void exportOrderReport(HttpServletResponse response) {
        response.setContentType("application/pdf");
        String date = new java.util.Date().toString();

        response.setHeader("Content-Disposition", "attachment; filename=order-report-" + date + ".pdf");
        orderService.exportOrderReport(response);
    }
}

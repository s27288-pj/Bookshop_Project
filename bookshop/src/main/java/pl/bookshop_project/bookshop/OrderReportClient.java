package pl.bookshop_project.bookshop;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

import com.bookapi.openapi.model.OrderReport;
import pl.bookshop_project.bookorder.OrderReport.OrderNewCopies;

import java.util.List;

@FeignClient(value = "order", url = "http://localhost:8081")
public interface OrderReportClient {
    @PostMapping("/order-report/sendOrderReport")
    ResponseEntity<List<OrderReport>> sendOrderReport(List<OrderNewCopies> orderReports);
}

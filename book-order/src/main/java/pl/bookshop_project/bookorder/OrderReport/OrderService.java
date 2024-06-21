package pl.bookshop_project.bookorder.OrderReport;

import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.PdfWriter;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.bookshop_project.bookorder.Exception.InvalidBookIdException;


import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    ResponseEntity<List<OrderReport>> receiveOrderReport (List<OrderNewCopies> orderNewCopies) {
        orderNewCopies.forEach(orderNewCopy -> {
            if (orderNewCopy.getNumberOfVisits() > 9) {
                if (orderNewCopy.getBookId() == null) {
                    throw new InvalidBookIdException("Book id cannot be null");
                }
                OrderReport orderReport = new OrderReport();
                orderReport = orderRepository.findById(orderNewCopy.getBookId()).orElse(new OrderReport());
                orderReport.setBookId(orderNewCopy.getBookId());
                orderReport.setOrderAmount(orderNewCopy.getNumberOfVisits() / 10);
                orderRepository.save(orderReport);
            }
        });
        return ResponseEntity.ok(orderRepository.findAll());
    }

    public void exportOrderReport (HttpServletResponse response) {
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, response.getOutputStream());

            document.open();
            Font font = new Font(Font.FontFamily.TIMES_ROMAN, 20, Font.BOLD);
            document.add(new com.itextpdf.text.Paragraph("Order Report", font));
            document.add(new com.itextpdf.text.Paragraph(""));
            document.add(new com.itextpdf.text.Paragraph("Book ID | Order Amount"));

            List<OrderReport> orderReports = orderRepository.findAll();
            for (OrderReport orderReport : orderReports) {
                document.add(new com.itextpdf.text.Paragraph(orderReport.getBookId() + " | " + orderReport.getOrderAmount()));
            }
            document.close();

        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }
}

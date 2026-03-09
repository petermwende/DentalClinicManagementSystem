import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import java.io.File;
import java.io.FileNotFoundException;

public class PDFTest {
    public static void main(String[] args) {
        try {
            String dest = "test.pdf";
            File file = new File(dest);

            PdfWriter writer = new PdfWriter(file);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            document.add(new Paragraph("Hello, iText! This is a test PDF document."));

            document.close();

            System.out.println("PDF Created Successfully: " + file.getAbsolutePath());
        } catch (FileNotFoundException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}



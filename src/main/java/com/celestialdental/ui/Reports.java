package com.celestialdental.ui;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.awt.Desktop;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.layout.borders.SolidBorder;
import java.util.HashMap;

public class Reports extends JFrame {
    private JComboBox<String> monthSelector;
    public Reports() {
        setTitle("Generate Reports");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        String[] months = {
            "December 2024", "January 2025", "February 2025", "March 2025"
        };
        monthSelector = new JComboBox<>(months);
        monthSelector.setFont(new Font("Segoe UI", Font.BOLD, 16));
        JButton btnGenerate = new JButton("Generate PDF Report");
        btnGenerate.setFont(new Font("Segoe UI", Font.BOLD, 18));
        btnGenerate.addActionListener(e -> generatePDF((String) monthSelector.getSelectedItem()));
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 1, 10, 10));
        panel.add(monthSelector);
        panel.add(btnGenerate);
        add(panel, BorderLayout.CENTER);
        setLocationRelativeTo(null);
        setVisible(true);
    }  private void generatePDF(String selectedMonth) {
        try {
            String fileName = selectedMonth.replace(" ", "_") + "_Report.pdf";
            File file = new File(fileName);
            PdfWriter writer = new PdfWriter(file);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);
            document.add(new Paragraph("Celestial Dental Clinic - " + selectedMonth + " Report")
                .setFontSize(22)
                .setBold()
                .setTextAlignment(TextAlignment.CENTER)
                .setFontColor(com.itextpdf.kernel.colors.ColorConstants.BLUE));
            document.add(new Paragraph("\n"));
            HashMap<String, String[][]> monthlyData = new HashMap<>();
            monthlyData.put("December 2024", new String[][]{
                {"1", "Peter Fred", "2025-01-05", "10:00 AM", "Confirmed", "Dr. Smith", "Checkup", "5000", "0", "MPesa"},
                {"2", "Mary Nyla", "2025-01-12", "12:15 PM", "Confirmed", "Dr. Adams", "Braces", "25000", "0", "Insurance"},
                {"3", "Jack Cactus", "2025-01-20", "02:45 PM", "Pending", "Dr. Khan", "Filling", "8000", "1000", "Cash"},
                {"4", "Dorcus Wanjiru", "2025-01-26", "04:20 PM", "Pending", "Dr. Patel", "Cleaning", "7000", "0", "MPesa"},
                {"5", "James Max", "2025-01-30", "09:30 AM", "Confirmed", "Dr. Carter", "Root Canal", "18000", "3000", "Bank Transfer"}
            });  
            monthlyData.put ("January 2025", new String[] []{
                {"6", "Kevin Mathai", "2025-02-03", "08:00 AM", "Confirmed", "Dr. Wekesa", "Tooth Extraction", "9000", "0", "MPesa"},
                {"7", "Diana Dee", "2025-02-09", "11:30 AM", "Confirmed", "Dr. Amina", "Braces", "28000", "2000", "Insurance"},
                {"8", "Mbugua Kimani", "2025-02-15", "01:45 PM", "Pending", "Dr. Mwangi", "Root Canal", "19000", "3000", "Cash"},
                {"9", "Esther Karisma", "2025-02-22", "04:10 PM", "Pending", "Dr. Patel", "Cleaning", "7500", "0", "Bank Transfer"},
                {"10", "Samuel Josh", "2025-02-27", "09:20 AM", "Confirmed", "Dr. Kamau", "Filling", "8500", "500", "MPesa"}
            });            
            monthlyData.put("February 2025", new String[][]{
                {"11", "Grace Wanjiku", "2025-03-05", "07:45 AM", "Confirmed", "Dr. Smith", "Checkup", "5000", "0", "MPesa"},
                {"12", "Joseph Edwin", "2025-03-12", "10:50 AM", "Confirmed", "Dr. Carter", "Braces", "30000", "0", "Insurance"},
                {"13", "Faith Maria", "2025-03-18", "02:30 PM", "Pending", "Dr. Patel", "Root Canal", "21000", "4000", "Cash"},
                {"14", "Daniel Duncan", "2025-03-23", "03:55 PM", "Pending", "Dr. Khan", "Tooth Extraction", "9000", "0", "Bank Transfer"},
                {"15", "Rebecca Maina", "2025-03-28", "08:30 AM", "Confirmed", "Dr. Wekesa", "Cleaning", "7000", "0", "MPesa"}
            });            
            monthlyData.put("March 2025", new String[][]{
                {"16", "Jean Fay", "2024-12-02", "09:00 AM", "Confirmed", "Dr. Smith", "Checkup", "4500", "0", "MPesa"},
                {"17", "Martin Rich", "2024-12-10", "10:30 AM", "Confirmed", "Dr. Adams", "Filling", "12000", "500", "Insurance"},
                {"18", "Linet Givens", "2024-12-18", "01:00 PM", "Pending", "Dr. Khan", "Root Canal", "20000", "4000", "Cash"},
                {"19", "Alvin Wambui", "2024-12-22", "03:30 PM", "Pending", "Dr. Patel", "Cleaning", "6000", "0", "MPesa"},
                {"20", "Elizabeth Mia", "2024-12-29", "08:45 AM", "Confirmed", "Dr. Carter", "Braces", "30000", "5000", "Bank Transfer"}
            });
            String[][] appointments = monthlyData.get(selectedMonth);
            int totalPatients = appointments.length;
            int totalRevenue = 0;
            int pendingBills = 0;
            for (String[] row : appointments) {
                totalRevenue += Integer.parseInt(row[7]);
                pendingBills += Integer.parseInt(row[8]);
            }
            Table summaryTable = new Table(2);
            summaryTable.setWidth(UnitValue.createPercentValue(100));
            summaryTable.addCell(new Cell().add(new Paragraph("Total Patients Attended").setBold()).setBorder(new SolidBorder(1)));
            summaryTable.addCell(new Cell().add(new Paragraph(String.valueOf(totalPatients))).setBorder(new SolidBorder(1)));
            summaryTable.addCell(new Cell().add(new Paragraph("Total Revenue Collected").setBold()).setBorder(new SolidBorder(1)));
            summaryTable.addCell(new Cell().add(new Paragraph("Ksh. " + totalRevenue)).setBorder(new SolidBorder(1)));
            summaryTable.addCell(new Cell().add(new Paragraph("Total Pending Bills").setBold()).setBorder(new SolidBorder(1)));
            summaryTable.addCell(new Cell().add(new Paragraph("Ksh. " + pendingBills)).setBorder(new SolidBorder(1)));
            document.add(summaryTable);
            document.add(new Paragraph("\n"));

            Table patientTable = new Table(10);
            patientTable.setWidth(UnitValue.createPercentValue(100));

            String[] headers = {
                "Patient ID", "Name", "Date", "Time", "Status", "Doctor",
                "Treatment", "Amount Paid (Ksh)", "Balance (Ksh)", "Payment Method"
            };
            for (String header : headers) {
                Cell cell = new Cell().add(new Paragraph(header).setBold());
                cell.setBackgroundColor(com.itextpdf.kernel.colors.ColorConstants.LIGHT_GRAY);
                patientTable.addCell(cell);
            }
            boolean alternateRowColor = false;
            for (String[] row : appointments) {
                for (String value : row) {
                    Cell cell = new Cell().add(new Paragraph(value));
                    if (alternateRowColor) {
                        cell.setBackgroundColor(com.itextpdf.kernel.colors.ColorConstants.LIGHT_GRAY);
                    }
                    patientTable.addCell(cell);
                }
                alternateRowColor = !alternateRowColor;
            }
            document.add(patientTable);
            document.add(new Paragraph("\nReport Generated Successfully!")
                .setFontSize(14)
                .setBold()
                .setFontColor(com.itextpdf.kernel.colors.ColorConstants.GREEN));
            document.close();
            JOptionPane.showMessageDialog(this, "Report saved as " + file.getAbsolutePath(), "Success", JOptionPane.INFORMATION_MESSAGE);
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(file);
            } else {
                JOptionPane.showMessageDialog(this, "Cannot open PDF automatically. Please open it manually.", "Warning", JOptionPane.WARNING_MESSAGE);
            }
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(this, "Error generating report: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error opening report: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }    public static void main(String[] args) {
        new Reports();
    }
}
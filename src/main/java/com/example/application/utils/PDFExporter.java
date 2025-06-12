package com.example.application.utils;

import java.io.OutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.TextAlignment;

public class PDFExporter {

    public static void exportToPdf(List<List<Participant>> groups, OutputStream outputStream) {
        try {
            PdfWriter writer = new PdfWriter(outputStream);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);
            Paragraph title = new Paragraph("Listado de grupos generados")
                    .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD))
                    .setFontSize(18)
                    .setTextAlignment(TextAlignment.CENTER);

            document.add(title);

            String dateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            Paragraph dateParagraph = new Paragraph("Fecha de impresión: " + dateTime)
                    .setFontSize(10)
                    .setTextAlignment(TextAlignment.RIGHT)
                    .setMarginBottom(20);
            document.add(dateParagraph);

            int groupNumber = 1;
            for (List<Participant> group : groups) {
                document.add(new Paragraph("Group " + groupNumber));
                for (Participant p : group) {
                    document.add(new Paragraph("- " + p.getNombre())); // adjust to match your class
                }
                document.add(new Paragraph(" "));
                groupNumber++;
            }

            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

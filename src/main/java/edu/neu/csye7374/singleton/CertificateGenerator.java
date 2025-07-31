package edu.neu.csye7374.singleton;

import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;

import java.io.FileOutputStream;

public class CertificateGenerator {
    private static CertificateGenerator instance;

    private CertificateGenerator() {}

    public static CertificateGenerator getInstance() {
        if (instance == null) {
            instance = new CertificateGenerator();
        }
        return instance;
    }

    public void generateCertificate(String filename, String userName, String courseName) throws Exception {
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(filename));
        document.open();
        document.add(new Paragraph("Certificate of Completion"));
        document.add(new Paragraph("Awarded to: " + userName));
        document.add(new Paragraph("For completing: " + courseName));
        document.close();
    }
}

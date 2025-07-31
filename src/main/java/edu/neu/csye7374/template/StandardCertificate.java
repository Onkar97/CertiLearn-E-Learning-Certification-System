package edu.neu.csye7374.template;

public class StandardCertificate extends CertificateTemplate {
    protected void layoutHeader() {
        System.out.println("=== Certificate of Achievement ===");
    }

    protected void layoutBody(String user, String course) {
        System.out.println("This certifies that " + user + " has completed the course: " + course);
    }

    protected void layoutFooter() {
        System.out.println("Date: Today\nSignature: Admin");
    }
}

package edu.neu.csye7374.proxy;

public class RealCertificateAccess implements CertificateAccess {
    public void download(String userRole) {
        System.out.println("Certificate downloaded successfully.");
    }
}

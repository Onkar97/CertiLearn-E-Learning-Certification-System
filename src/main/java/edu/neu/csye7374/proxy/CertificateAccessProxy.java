package edu.neu.csye7374.proxy;

public class CertificateAccessProxy implements CertificateAccess {
    private RealCertificateAccess realAccess = new RealCertificateAccess();

    public void download(String userRole) {
        if ("STUDENT".equals(userRole)) {
            realAccess.download(userRole);
        } else {
            System.out.println("Access denied. Only students can download certificates.");
        }
    }
}

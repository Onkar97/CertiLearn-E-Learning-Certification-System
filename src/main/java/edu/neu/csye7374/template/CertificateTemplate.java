package edu.neu.csye7374.template;

public abstract class CertificateTemplate {
    public final void generate(String userName, String courseName) {
        layoutHeader();
        layoutBody(userName, courseName);
        layoutFooter();
    }

    protected abstract void layoutHeader();
    protected abstract void layoutBody(String user, String course);
    protected abstract void layoutFooter();
}

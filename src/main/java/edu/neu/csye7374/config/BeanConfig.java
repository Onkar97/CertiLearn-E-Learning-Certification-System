package edu.neu.csye7374.config;

import edu.neu.csye7374.singleton.CertificateGenerator;
import edu.neu.csye7374.proxy.CertificateAccessProxy;
import edu.neu.csye7374.proxy.RealCertificateAccess;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean
    public CertificateGenerator certificateGenerator() {
        return CertificateGenerator.getInstance();
    }

    @Bean
    public CertificateAccessProxy certificateAccessProxy() {
        return new CertificateAccessProxy();
    }
}

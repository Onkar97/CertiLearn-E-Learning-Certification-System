package edu.neu.csye7374.service;

import edu.neu.csye7374.model.Certificate;
import edu.neu.csye7374.repository.CertificateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CertificateService {

    @Autowired
    private CertificateRepository certificateRepository;

    public Optional<Certificate> getCertificateById(Long id) {
        return certificateRepository.findById(id);
    }
}
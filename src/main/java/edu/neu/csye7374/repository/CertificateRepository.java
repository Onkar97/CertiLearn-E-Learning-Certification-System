package edu.neu.csye7374.repository;

import edu.neu.csye7374.model.Certificate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CertificateRepository extends JpaRepository<Certificate, Long> {}

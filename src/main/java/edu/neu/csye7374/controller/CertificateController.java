package edu.neu.csye7374.controller;

import edu.neu.csye7374.model.Certificate;
import edu.neu.csye7374.repository.CertificateRepository;
import edu.neu.csye7374.singleton.CertificateGenerator;
import edu.neu.csye7374.proxy.CertificateAccessProxy;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.util.Optional;

@RestController
@RequestMapping("/api/certificates")
@RequiredArgsConstructor
public class CertificateController {

    private final CertificateRepository certificateRepository;
    private final CertificateGenerator certificateGenerator;
    private final CertificateAccessProxy certificateAccessProxy;

    @PostMapping
    public Certificate createCertificate(@RequestBody Certificate certificate) {
        return certificateRepository.save(certificate);
    }

    @GetMapping("/{id}/download")
    public ResponseEntity<byte[]> downloadCertificate(@PathVariable Long id) {
        Optional<Certificate> certificateOptional = certificateRepository.findById(id);
        if (certificateOptional.isPresent()) {
            Certificate certificate = certificateOptional.get();
            try {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                certificateGenerator.generateCertificate(certificate.getUser().getName(), certificate.getCourse().getTitle(), baos);
                byte[] pdfBytes = baos.toByteArray();

                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"certificate_" + id + ".pdf\"")
                        .contentType(MediaType.APPLICATION_PDF)
                        .body(pdfBytes);
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.status(500).build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

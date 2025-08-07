package edu.neu.csye7374.controller;

import edu.neu.csye7374.model.Certificate;
import edu.neu.csye7374.service.CertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@RestController
@RequestMapping("/api/certificate")
public class CertificateController {

    @Autowired
    private CertificateService certificateService;

    @GetMapping("/view/{id}")
    public ResponseEntity<Certificate> viewCertificate(@PathVariable Long id) {
        Optional<Certificate> certificate = certificateService.getCertificateById(id);
        return certificate.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> downloadCertificate(@PathVariable Long id) {
        Optional<Certificate> certificateOptional = certificateService.getCertificateById(id);
        if (certificateOptional.isPresent()) {
            Certificate certificate = certificateOptional.get();
            Path path = Paths.get(certificate.getFilePath());
            Resource resource = null;
            try {
                resource = new UrlResource(path.toUri());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
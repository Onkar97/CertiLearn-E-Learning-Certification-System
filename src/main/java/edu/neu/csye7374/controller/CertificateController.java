package edu.neu.csye7374.controller;

import edu.neu.csye7374.dto.CertificateRequest;
import edu.neu.csye7374.model.Certificate;
import edu.neu.csye7374.model.Course;
import edu.neu.csye7374.model.User;
import edu.neu.csye7374.service.CertificateService;
import edu.neu.csye7374.service.CourseService;
import edu.neu.csye7374.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Optional;

@RestController
@RequestMapping("/api/certificate")
public class CertificateController {

    @Autowired
    private CertificateService certificateService;
    @Autowired
    private MyUserDetailsService userService;        // You need to create/use this service

    @Autowired
    private CourseService courseService;


    @GetMapping("/view/{id}")
    public ResponseEntity<Certificate> viewCertificate(@PathVariable Long id) {
        Optional<Certificate> certificate = certificateService.getCertificateById(id);
        return certificate.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/create")
    public ResponseEntity<Certificate> createCertificate(@RequestBody CertificateRequest request) {
        // You need to get User and Course entities by their IDs
        User user = userService.getUserById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Course course = courseService.getCourseById(request.getCourseId())
                .orElseThrow(() -> new RuntimeException("Course not found"));

        Certificate certificate = new Certificate();
        certificate.setUser(user);
        certificate.setCourse(course);
        certificate.setIssueDate(request.getIssueDate() != null ? request.getIssueDate() : LocalDate.now());

        Certificate savedCertificate = certificateService.saveCertificate(certificate);
        return ResponseEntity.ok(savedCertificate);
    }
}
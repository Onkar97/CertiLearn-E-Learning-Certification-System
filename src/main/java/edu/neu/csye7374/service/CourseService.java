package edu.neu.csye7374.service;

import edu.neu.csye7374.model.Course;
import edu.neu.csye7374.model.User;
import edu.neu.csye7374.model.Certificate;
import edu.neu.csye7374.repository.CourseRepository;
import edu.neu.csye7374.repository.CertificateRepository;
import edu.neu.csye7374.singleton.CertificateGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;
    private final CertificateRepository certificateRepository;

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public Certificate issueCertificate(User user, Course course) throws Exception {
        Certificate cert = new Certificate();
        cert.setUser(user);
        cert.setCourse(course);
        cert.setIssueDate(LocalDate.now());

        String filePath = "certificates/" + user.getId() + "_" + course.getId() + ".pdf";
        cert.setFilePath(filePath);
        certificateRepository.save(cert);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        CertificateGenerator.getInstance().generateCertificate(user.getName(), course.getTitle(), baos);


        return cert;
    }
}
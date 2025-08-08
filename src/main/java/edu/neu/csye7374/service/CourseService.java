package edu.neu.csye7374.service;

import edu.neu.csye7374.model.Course;
import edu.neu.csye7374.model.User;
import edu.neu.csye7374.model.UserCourse;
import edu.neu.csye7374.repository.CourseRepository;
import edu.neu.csye7374.repository.UserCourseRepository;
import edu.neu.csye7374.singleton.CertificateGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;
    private final UserCourseRepository userCourseRepository;

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public Course addCourse(Course course) {
        return courseRepository.save(course);
    }

    public void enrollInCourse(Long userId, Long courseId) {
        User user = new User();
        user.setId(userId);
        Course course = new Course();
        course.setId(courseId);

        UserCourse userCourse = new UserCourse();
        userCourse.setUser(user);
        userCourse.setCourse(course);
        userCourseRepository.save(userCourse);
    }

    public void dropCourse(Long userId, Long courseId) {
        userCourseRepository.findByUserIdAndCourseId(userId, courseId).ifPresent(userCourseRepository::delete);
    }
    public Optional<Course> getCourseById(Long id) {
        return courseRepository.findById(id);
    }
}
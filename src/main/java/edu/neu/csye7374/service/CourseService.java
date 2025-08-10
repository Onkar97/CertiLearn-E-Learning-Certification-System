package edu.neu.csye7374.service;

import edu.neu.csye7374.dto.CourseDTO;
import edu.neu.csye7374.model.Course;
import edu.neu.csye7374.model.User;
import edu.neu.csye7374.model.UserCourse;
import edu.neu.csye7374.repository.CourseRepository;
import edu.neu.csye7374.repository.UserCourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;
    private final UserCourseRepository userCourseRepository;

    public List<CourseDTO> listAllCoursesWithInstructor() {
        return courseRepository.findAllWithInstructor();
    }

    public List<CourseDTO> listEnrolledCourses(Long userId) {
        return courseRepository.findEnrolledByUserId(userId);
    }

    public List<Course> listInstructorCourses(Long instructorId) {
        return courseRepository.findByInstructor_Id(instructorId);
    }

    public Course addCourse(Course course) {
        return courseRepository.save(course);
    }

    public void enrollInCourse(Long userId, Long courseId) {
        User user = new User(); user.setId(userId);
        Course course = new Course(); course.setId(courseId);

        UserCourse uc = new UserCourse();
        uc.setUser(user);
        uc.setCourse(course);
        userCourseRepository.save(uc);
    }
    public Course getCourseById(Long courseId) {
        return courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found with id: " + courseId));
    }

    public void dropCourse(Long userId, Long courseId) {
        userCourseRepository.findByUserIdAndCourseId(userId, courseId)
                .ifPresent(userCourseRepository::delete);
    }
}

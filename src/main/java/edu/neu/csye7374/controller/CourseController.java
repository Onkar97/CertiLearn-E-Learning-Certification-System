package edu.neu.csye7374.controller;

import edu.neu.csye7374.dto.CourseDTO;
import edu.neu.csye7374.model.Course;
import edu.neu.csye7374.model.CustomUserDetails;
import edu.neu.csye7374.model.User;
import edu.neu.csye7374.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;

    // All Courses with instructor name
    @GetMapping
    public List<CourseDTO> listAll() {
        return courseService.listAllCoursesWithInstructor();
    }

    // Student: my enrolled courses (uses authenticated user)
    @GetMapping("/enrolled")
    public List<CourseDTO> myEnrolled(Authentication auth) {
        Long userId = ((CustomUserDetails) auth.getPrincipal()).getId();
        return courseService.listEnrolledCourses(userId);
    }

    // Instructor: my courses
    @GetMapping("/mine")
    public List<Course> myCourses(Authentication auth) {
        Long instructorId = ((CustomUserDetails) auth.getPrincipal()).getId();
        return courseService.listInstructorCourses(instructorId);
    }

    // Create course (optional: force instructor from current user)
    @PostMapping
    public Course addCourse(@RequestBody Course course, Authentication auth) {
        // If you want to enforce instructor is the current user, uncomment:
        // Long instructorId = ((CustomUserDetails) auth.getPrincipal()).getId();
        // User instructor = new User(); instructor.setId(instructorId);
        // course.setInstructor(instructor);
        return courseService.addCourse(course);
    }

    // Enroll current user in a course
    @PostMapping("/enroll/{courseId}")
    public ResponseEntity<?> enrollCourse(@PathVariable Long courseId, Authentication auth) {
        Long userId = ((CustomUserDetails) auth.getPrincipal()).getId();
        courseService.enrollInCourse(userId, courseId);
        return ResponseEntity.ok("Enrolled in course successfully");
    }

    // Drop current user's enrollment
    @DeleteMapping("/drop/{courseId}")
    public ResponseEntity<?> dropCourse(@PathVariable Long courseId, Authentication auth) {
        Long userId = ((CustomUserDetails) auth.getPrincipal()).getId();
        courseService.dropCourse(userId, courseId);
        return ResponseEntity.ok("Dropped course successfully");
    }
}

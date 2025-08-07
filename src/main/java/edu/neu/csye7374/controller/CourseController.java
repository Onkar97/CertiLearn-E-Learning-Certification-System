package edu.neu.csye7374.controller;

import edu.neu.csye7374.model.Course;
import edu.neu.csye7374.model.CustomUserDetails;
import edu.neu.csye7374.model.User;
import edu.neu.csye7374.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @GetMapping
    public List<Course> getAllCourses() {
        return courseService.getAllCourses();
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('INSTRUCTOR')")
    public Course addCourse(@RequestBody Course course) {
        return courseService.addCourse(course);
    }

    @PostMapping("/enroll/{courseId}")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<?> enrollCourse(@PathVariable Long courseId, Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Long userId = userDetails.getUser().getId();
        courseService.enrollInCourse(userId, courseId);
        return ResponseEntity.ok("Enrolled in course successfully");
    }

    @DeleteMapping("/drop/{courseId}")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<?> dropCourse(@PathVariable Long courseId, Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Long userId = userDetails.getUser().getId();
        courseService.dropCourse(userId, courseId);
        return ResponseEntity.ok("Dropped course successfully");
    }
}
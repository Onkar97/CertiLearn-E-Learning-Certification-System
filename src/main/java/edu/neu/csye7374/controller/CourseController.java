package edu.neu.csye7374.controller;

import edu.neu.csye7374.model.Course;
import edu.neu.csye7374.model.CustomUserDetails;
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
    public Course addCourse(@RequestBody Course course) {
        return courseService.addCourse(course);
    }

    @PostMapping("/enroll/{courseId}")
    public ResponseEntity<?> enrollCourse(@PathVariable Long courseId, @RequestParam Long userId) {
        courseService.enrollInCourse(userId, courseId);
        return ResponseEntity.ok("Enrolled in course successfully");
    }

    @DeleteMapping("/drop/{courseId}")
    public ResponseEntity<?> dropCourse(@PathVariable Long courseId, @RequestParam Long userId) {
        courseService.dropCourse(userId, courseId);
        return ResponseEntity.ok("Dropped course successfully");
    }
}
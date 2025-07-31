package edu.neu.csye7374.controller;

import edu.neu.csye7374.model.Course;
import edu.neu.csye7374.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseRepository courseRepository;

    @GetMapping
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    @PostMapping
    public Course addCourse(@RequestBody Course course) {
        return courseRepository.save(course);
    }
}

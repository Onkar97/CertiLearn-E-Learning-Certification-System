package edu.neu.csye7374.repository;

import edu.neu.csye7374.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {}

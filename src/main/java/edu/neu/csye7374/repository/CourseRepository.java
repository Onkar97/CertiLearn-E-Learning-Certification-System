package edu.neu.csye7374.repository;

import edu.neu.csye7374.dto.CourseDTO;
import edu.neu.csye7374.model.Course;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {

    @Query("""
           select new edu.neu.csye7374.dto.CourseDTO(
               c.id, c.title, c.description, coalesce(i.name, '')
           )
           from Course c
           left join c.instructor i
           """)
    List<CourseDTO> findAllWithInstructor();

    @Query("""
           select new edu.neu.csye7374.dto.CourseDTO(
               c.id, c.title, c.description, coalesce(i.name, '')
           )
           from UserCourse uc
           join uc.course c
           left join c.instructor i
           where uc.user.id = :userId
           """)
    List<CourseDTO> findEnrolledByUserId(@Param("userId") Long userId);

    List<Course> findByInstructor_Id(Long instructorId);
}

package edu.neu.csye7374.repository;
import edu.neu.csye7374.model.Grade;
import org.springframework.data.jpa.repository.JpaRepository;
public interface GradeRepository extends JpaRepository<Grade, Long> {

}
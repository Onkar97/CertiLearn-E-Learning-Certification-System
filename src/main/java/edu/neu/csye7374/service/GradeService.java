package edu.neu.csye7374.service;

import edu.neu.csye7374.model.Grade;
import edu.neu.csye7374.repository.GradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GradeService {

    @Autowired
    private GradeRepository gradeRepository;

    public List<Grade> getGradesByStudentId(Long studentId) {
        return gradeRepository.findByStudentId(studentId);
    }

    public List<Grade> getAllGrades() {
        return gradeRepository.findAll();
    }

    public Grade giveGrade(Grade grade) {
        return gradeRepository.save(grade);
    }
}
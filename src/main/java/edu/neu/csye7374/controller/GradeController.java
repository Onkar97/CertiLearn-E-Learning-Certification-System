package edu.neu.csye7374.controller;

import edu.neu.csye7374.model.Grade;
import edu.neu.csye7374.service.GradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/grades")
public class GradeController {

    @Autowired
    private GradeService gradeService;

    @GetMapping("/view/student/{studentId}")
    public List<Grade> viewMyGrades(@PathVariable Long studentId) {
        return gradeService.getGradesByStudentId(studentId);
    }

    @GetMapping("/view/all")
    public List<Grade> viewAllGrades() {
        return gradeService.getAllGrades();
    }

    @PostMapping("/give")
    public Grade giveGrade(@RequestBody Grade grade) {
        return gradeService.giveGrade(grade);
    }
}
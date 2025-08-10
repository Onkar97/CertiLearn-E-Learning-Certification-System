package edu.neu.csye7374.controller;

import edu.neu.csye7374.model.Grade;
import edu.neu.csye7374.model.CustomUserDetails;
import edu.neu.csye7374.service.GradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

import java.util.List;

@RestController
@RequestMapping("/api/grades")
public class GradeController {

    @Autowired
    private GradeService gradeService;

    // New: student can view their own grades without passing studentId
    @GetMapping("/me")
    public List<Grade> viewMyGrades(Authentication auth) {
        Long studentId = ((CustomUserDetails) auth.getPrincipal()).getId();
        return gradeService.getGradesByStudentId(studentId);
    }

    @GetMapping("/view/student/{studentId}")
    public List<Grade> viewGradesByStudent(@PathVariable Long studentId) {
        return gradeService.getGradesByStudentId(studentId);
    }

    @GetMapping("/view/all")
    public List<Grade> viewAllGrades() {
        return gradeService.getAllGrades();
    }

    @PostMapping("/give")
    public Grade giveGrade(@RequestBody Grade grade) {
        return gradeService.giveGrade(grade); // or gradeService.giveGrade(grade);
    }
}

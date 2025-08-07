package edu.neu.csye7374.controller;

import edu.neu.csye7374.model.CustomUserDetails;
import edu.neu.csye7374.model.Grade;
import edu.neu.csye7374.service.GradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/grades")
public class GradeController {

    @Autowired
    private GradeService gradeService;

    @GetMapping("/view/student")
    @PreAuthorize("hasRole('STUDENT')")
    public List<Grade> viewMyGrades(Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Long studentId = userDetails.getUser().getId();
        return gradeService.getGradesByStudentId(studentId);
    }

    @GetMapping("/view/all")
    @PreAuthorize("hasRole('INSTRUCTOR')")
    public List<Grade> viewAllGrades() {
        return gradeService.getAllGrades();
    }

    @PostMapping("/give")
    @PreAuthorize("hasRole('INSTRUCTOR')")
    public Grade giveGrade(@RequestBody Grade grade) {
        return gradeService.giveGrade(grade);
    }
}
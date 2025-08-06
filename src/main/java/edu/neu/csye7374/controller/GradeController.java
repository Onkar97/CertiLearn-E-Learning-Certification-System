package edu.neu.csye7374.controller;

import edu.neu.csye7374.model.Grade;
import edu.neu.csye7374.repository.GradeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
@RestController@RequestMapping("/api/grades")
@RequiredArgsConstructor
public class GradeController {
    private final GradeRepository gradeRepository;
    @PostMapping
    public Grade giveGrade(@RequestBody Grade grade) {
        return gradeRepository.save(grade);
    }

}